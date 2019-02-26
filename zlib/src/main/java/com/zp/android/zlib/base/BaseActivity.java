package com.zp.android.zlib.base;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.KeyEvent;

import com.zp.android.zlib.R;
import com.zp.android.zlib.di.BaseComponent;
import com.zp.android.zlib.utils.StringUtils;

import java.util.List;

public abstract class BaseActivity<C extends BaseComponent> extends AppCompatActivity {

    private C component;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        component = createComponent();
        inject(component);
        setStatusBarColor();
        initProgressDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        @SuppressLint("RestrictedApi") List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    @SuppressLint("RestrictedApi")
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean res = false;
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            List<Fragment> fragments = getSupportFragmentManager().getFragments();
            if (fragments != null) {
                for (Fragment fragment : fragments) {
                    if (fragment instanceof BaseFragment) {
                        res = ((BaseFragment)fragment).onBackPressed();
                    }
                }
            }
        }
        return res || super.onKeyDown(keyCode, event);
    }

    @SuppressLint("RestrictedApi")
    public final Fragment findFragmentByTag(String tag) {
        if (StringUtils.isEmpty(tag) || getSupportFragmentManager().getFragments() == null) { return null; }
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            if (fragment != null && tag.equals(fragment.getTag())) {
                return fragment;
            }
        }
        return null;
    }

    public final void replace(final int containerId, final Fragment frag, final String tag) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                if (TextUtils.isEmpty(tag)) {
                    fragmentTransaction.replace(containerId, frag);
                }
                else {
                    fragmentTransaction.replace(containerId, frag, tag);
                }
                fragmentTransaction.commitAllowingStateLoss();
            }
        });
    }

    private void initProgressDialog() {
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progress));
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(true);
    }

    public final void showProgressDialog(String msg) {
        showProgressDialog(msg, true);
    }

    public final void showProgressDialog(final String msg, final boolean cancel) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog == null) { return; }
                mProgressDialog.setCancelable(cancel);
                mProgressDialog.setCanceledOnTouchOutside(cancel);
                mProgressDialog.setMessage(msg);
                if (!mProgressDialog.isShowing() && !isFinishing()) {
                    mProgressDialog.show();
                }
            }
        });
    }

    public final void closeProgressDialog() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mProgressDialog == null) { return; }
                mProgressDialog.setCancelable(true);
                mProgressDialog.setCanceledOnTouchOutside(true);
                mProgressDialog.dismiss();
            }
        });
    }

    public boolean isShowing() {
        return mProgressDialog != null && mProgressDialog.isShowing();
    }

    /**
     * 设置状态栏颜色---如果某个页面需要定制状态栏颜色，请在setContentView()后重写此方法
     */
    public void setStatusBarColor() {
        //StatusBarCompat.setStatusBarColor(this, Color.parseColor("#0b8044"));
    }

    public C getComponent() {
        return component;
    }

    protected abstract int getLayoutId();

    protected abstract C createComponent();

    protected abstract void inject(C component);
}
