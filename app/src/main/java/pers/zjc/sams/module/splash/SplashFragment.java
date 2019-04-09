package pers.zjc.sams.module.splash;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.zp.android.zlib.base.BaseFragment;
import com.zp.android.zlib.utils.EncryptUtils;
import com.zp.android.zlib.utils.PhoneUtils;
import com.zp.android.zlib.utils.ToastUtils;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.RuntimePermissions;
import pers.zjc.sams.R;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.common.ScmpUtils;
import pers.zjc.sams.module.main.MainActivity;

//@RuntimePermissions
public class SplashFragment extends BaseFragment implements View.OnClickListener {

    private static final int REQUEST_CODE_USAGE = 1;
    private static final int REQUEST_CODE_OTHER = 2;

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

    Thread thread = new Thread(new Runnable() {
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
        initView();
        initPermission();
    }

    private void initView() {
        mTvCountDownTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        mIvSplash.setImageResource(R.drawable.app_logo);
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



//    @OnPermissionDenied(
//            { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA })
//    @OnNeverAskAgain(
//            { Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA })


    private void initPermission() {
        //流量统计需要用到查看设备使用情况权限
        if (!ScmpUtils.checkUsagePermission(getActivity())) {
            Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
            startActivityForResult(intent, REQUEST_CODE_USAGE);
        } else {
            checkOtherPermissions();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_OTHER:
                if (grantResults.length > 0) {
                    for (int result : grantResults) {
                        if (result != PackageManager.PERMISSION_GRANTED) {
                            showShortToast("您拒绝了权限授权, 将无法正常使用部分功能");
                        }
                    }
                }
                thread.start();
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUEST_CODE_USAGE:
                if (!ScmpUtils.checkUsagePermission(getActivity())) {
                    ToastUtils.showShort("请开启智勤查看使用情况权限，否则无法统计设备使用流量。");
                    Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                    startActivityForResult(intent, REQUEST_CODE_USAGE);
                } else {
                    checkOtherPermissions();
                }
                break;
            default:
                break;
        }
    }

    private void checkOtherPermissions() {
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.
                permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_CODE_OTHER);
        } else {
            thread.start();
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
