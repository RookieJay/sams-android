package pers.zjc.sams.module.splash;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zp.android.zlib.base.BaseFragment;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import pers.zjc.sams.R;
import pers.zjc.sams.module.main.MainActivity;

public class SplashFragment extends BaseFragment implements View.OnClickListener {

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_splash;
    }

    @BindView(R.id.iv_splash)
    ImageView mIvSplash;
    @BindView(R.id.tv_countdown_time)
    TextView mTvCountDownTime;
    DelayHandler handler = new DelayHandler(this);
    private Unbinder unbinder;

    MainActivity mainActivity;

    Thread thread;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivity) {
            mainActivity = (MainActivity) activity;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        unbinder = ButterKnife.bind(this, getView());
//        statusBarHide(mainActivity);
        mTvCountDownTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mIvSplash.setImageResource(R.drawable.app_logo);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 5; i >= 0; i--) {
                    Message msg = handler.obtainMessage();
                    msg.arg1 = i;
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
    }

    private void finish() {
        if (isAdded()) {
            MainActivity activity;
            if (null != (activity = (MainActivity)getActivity())) {
                activity.switchToLoginFragment();
            }
        }
    }

    @Override
    public void onClick(View view) {

    }

    /**
     * 设置Activity的statusBar隐藏
     * @param
     */
    public static void statusBarHide(Activity activity){
        // 代表 5.0 及以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = activity.getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE //两个FLAG一起用表示会让应用的主体内容占用系统状态栏的空间
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION;    //让应用的主体内容占用系统导航栏的空间
            decorView.setSystemUiVisibility(option);
            activity.getWindow().setNavigationBarColor(Color.TRANSPARENT);   //设置导航栏透明
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
            ActionBar actionBar = activity.getActionBar();
            if (actionBar != null) {
                actionBar.hide();
            }
            return;
        }
        // versionCode > 4.4 and versionCode < 5.0
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (thread != null) {
            thread = null;
        }
//        if (handler != null) {
//            handler.removeMessages(0);
//            handler = null;
//        }
        unbinder.unbind();
    }

    static class DelayHandler extends Handler {

        private WeakReference<SplashFragment> weakReference;

        public DelayHandler(SplashFragment fragment) {
            weakReference = new WeakReference<>(fragment);
        }

        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            final SplashFragment fragment = weakReference.get();
            super.handleMessage(msg);
            if (null != fragment) {
                switch (msg.what) {
                    case 0:
                        if (msg.arg1 == 0) {
                            fragment.finish();
                        } else if (fragment.mTvCountDownTime != null){
                            fragment.mTvCountDownTime.setText(msg.arg1+"s");
                        }
                        break;
                    default:
                        break;
                }
            }

        }
    }
}
