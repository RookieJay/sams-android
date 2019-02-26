package pers.zjc.sams.module.main;

import android.content.Intent;
import android.support.v4.app.Fragment;

import com.zp.android.zlib.base.BaseActivity;

import pers.zjc.sams.R;
import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.data.ModuleInfo;

public class Navigator {

    public static final String TAG = Navigator.class.getSimpleName();
    private BaseActivity activity;
    private AppConfig appConfig;

    Navigator(BaseActivity activity, AppConfig appConfig) {
        this.activity = activity;
        this.appConfig = appConfig;
    }

    public void navigateTo(String tag) {
        Fragment fragment = activity.findFragmentByTag(tag);
        if (fragment == null) {
            // fragment = new HomeFragment();
        }
        activity.replace(R.id.container, fragment, tag);
    }

    public void navigateTo(ModuleInfo info) {
        Intent intent;
        //        switch (info.getModuleId()){
        //            //// TODO: 2017/11/1 相关模块的跳转逻辑
        //        }
    }
}
