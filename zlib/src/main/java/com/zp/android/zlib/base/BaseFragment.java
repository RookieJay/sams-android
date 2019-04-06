package com.zp.android.zlib.base;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.zp.android.zlib.utils.KeyboardUtils;
import com.zp.android.zlib.utils.ToastUtils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private final String TAG = this.getClass().getSimpleName();

    private boolean isFragmentVisible;
    private boolean isReuseView;
    private boolean isFirstVisible;
    private View rootView;
    private Unbinder unbinder;
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        initVariable();
        initNetWorkStatus();
    }

    
    
    private void initNetWorkStatus() {
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
//        intentFilter.addAction(action);
        networkChangeReceiver = new NetworkChangeReceiver();
        getContext().registerReceiver(networkChangeReceiver, intentFilter);
    }

    @Override
    public final View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        int layoutId = getLayoutId();
        if (layoutId == 0) {
            throw new IllegalArgumentException(
                    "getLayoutId() returned 0, which is not allowed. " + "If you don't want to use getLayoutId() but implement your own view " + "for this fragment manually, then you have to override onCreateView();");
        }
        else {
            beforeContentView();
            View view = inflater.inflate(layoutId, container, false);
            view.setClickable(true);
//            unbinder = ButterKnife.bind(this, view);
            return view;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        //如果setUserVisibleHint()在rootView创建前调用时，那么
        //就等到rootView创建完后才回调onFragmentVisibleChange(true)
        //保证onFragmentVisibleChange()的回调发生在rootView创建完成之后，以便支持ui操作
        if (rootView == null) {
            rootView = view;
            if (getUserVisibleHint()) {
                if (isFirstVisible) {
                    onFragmentFirstVisible();
                    isFirstVisible = false;
                }
                onFragmentVisibleChange(true);
                isFragmentVisible = true;
            }
        }
        super.onViewCreated(view, savedInstanceState);
    }

    //setUserVisibleHint()在Fragment创建时会先被调用一次，传入isVisibleToUser = false
    //如果当前Fragment可见，那么setUserVisibleHint()会再次被调用一次，传入isVisibleToUser = true
    //如果Fragment从可见->不可见，那么setUserVisibleHint()也会被调用，传入isVisibleToUser = false
    //总结：setUserVisibleHint()除了Fragment的可见状态发生变化时会被回调外，在new Fragment()时也会被回调
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //setUserVisibleHint()有可能在fragment的生命周期外被调用
        if (rootView == null) {
            return;
        }
        if (isFirstVisible && isVisibleToUser) {
            onFragmentFirstVisible();
            isFirstVisible = false;
        }
        if (isVisibleToUser) {
            onFragmentVisibleChange(true);
            isFragmentVisible = true;
            return;
        }
        if (isFragmentVisible) {
            isFragmentVisible = false;
            onFragmentVisibleChange(false);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    protected void initVariable() {
        isFirstVisible = true;
        isFragmentVisible = false;
        rootView = null;
        isReuseView = true;
    }

    /**
     *
     * @param isReuse
     */
    protected void reuseView(boolean isReuse) {
        isReuseView = isReuse;
    }

    /**
     * 去除setUserVisibleHint()多余的回调场景，保证只有当fragment可见状态发生变化时才回调
     * 回调时机在view创建完后，所以支持ui操作，解决在setUserVisibleHint()里进行ui操作有可能报null异常的问题
     *
     * 可在该回调方法里进行一些ui显示与隐藏
     *
     * @param isVisible true  不可见 -> 可见 false 可见  -> 不可见
     */
    protected void onFragmentVisibleChange(boolean isVisible) {

    }

    /**
     * 在fragment首次可见时回调，可用于加载数据，防止每次进入都重复加载数据
     */
    protected void onFragmentFirstVisible() {

    }

    protected boolean isFragmentVisible() {
        return isFragmentVisible;
    }

    protected final View findViewById(int id) {
        return (getView() != null && id >= 0) ? getView().findViewById(id) : null;
    }

    protected final Fragment findFragmentByTag(String tag) {
        if (isAdded() && getActivity() instanceof BaseActivity) {
            return ((BaseActivity)getActivity()).findFragmentByTag(tag);
        }
        return null;
    }

    protected ActionBar setSupportActionBar(Toolbar toolbar) {
        if (isAdded() && getActivity() instanceof AppCompatActivity && toolbar != null) {
            AppCompatActivity appCompatActivity = (AppCompatActivity)getActivity();
            appCompatActivity.setSupportActionBar(toolbar);
            return appCompatActivity.getSupportActionBar();
        }
        return null;
    }

    /**
     * Activity返回键事件回调
     *
     * @return true表示已消费该事件，false表示未消费该事件
     */
    @SuppressLint("RestrictedApi")
    public boolean onBackPressed() {
        boolean res = false;
        List<Fragment> childFragments = getChildFragmentManager().getFragments();
        if (childFragments != null) {
            for (Fragment child : childFragments) {
                if (child instanceof BaseFragment) {
                    boolean childRes = ((BaseFragment)child).onBackPressed();
                    if (!res) {
                        res = childRes;
                    }
                }
            }
        }
        return res;
    }

    protected final void runOnUiThread(Runnable action) {
        if (isAdded() && getActivity() != null) {
            getActivity().runOnUiThread(action);
        }
    }

    protected void initHeader(String titleText, boolean showButton) {
        //TextView title = (TextView)findViewById(R.id.txt_title);
        //if (title != null) { title.setText(titleText); }
        //Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        //        if (toolbar != null) {
        //            if (showButton) {
        //                //设置后退按钮
        //                toolbar.setNavigationIcon(R.mipmap.icon_back);
        //                toolbar.setNavigationOnClickListener(new View.OnClickListener() {
        //                    @Override
        //                    public void onClick(View v) {
        //                        if (isAdded()) {
        //                            getActivity().finish();
        //                        }
        //                    }
        //                });
        //                //显示toolbar右上角按键
        //                if (toolbar.getMenu().size() == 0) {
        //                    toolbar.inflateMenu(R.menu.toolbar_menu_base);
        //                    toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
        //                        @Override
        //                        public boolean onMenuItemClick(MenuItem item) {
        //                            if (item.getItemId() == R.id.btn_back) {
        //                                NativeUtils.backToHome(getActivity());
        //                            }
        //                            return false;
        //                        }
        //                    });
        //                }
        //            }
        //        }
    }

    protected final void showProgressDialogInternal(String msg) {
        showProgressDialogInternal(msg, true);
    }

    protected final void showProgressDialogInternal(final String msg, final boolean cancel) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isAdded() && getActivity() instanceof BaseActivity) {
                    BaseActivity activity = (BaseActivity)getActivity();
                    if (!activity.isShowing()) {
                        activity.showProgressDialog(msg, cancel);
                    }
                }
            }
        });
    }

    protected final void closeProgressDialogInternal() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isAdded() && getActivity() instanceof BaseActivity) {
                    ((BaseActivity)getActivity()).closeProgressDialog();
                }
            }
        });
    }

    public void onNewIntent(Intent intent) {
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof BaseFragment) {
                ((BaseFragment)fragment).onNewIntent(intent);
            }
        }
    }

    protected void beforeContentView() { }

    protected abstract int getLayoutId();

    protected final void showShortToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showShort(msg);
            }
        });
    }

    protected final void showLongToast(final String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.showLong(msg);
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    protected final void hideKeyboardWhenLostFocus(View view) {
        view.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    KeyboardUtils.hideSoftInput(getView());
                }
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
//        unbinder.unbind();
        if (getContext() != null && networkChangeReceiver != null) {
            getContext().unregisterReceiver(networkChangeReceiver);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }

    public void systemExit() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

    public void restartApp() {
        Intent i = getContext().getPackageManager().getLaunchIntentForPackage(getContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectionManager = (ConnectivityManager)
                    getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectionManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()) {
//                Toast.makeText(context, "network is available",
//                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "网络异常，请检查网络状态",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }



}
