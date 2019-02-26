package pers.zjc.sams.module.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.KeyEvent;

import com.zp.android.zlib.base.BaseActivity;
import com.zp.android.zlib.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import pers.zjc.sams.R;
import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.entity.AttenceRecord;
import pers.zjc.sams.module.login.view.LoginFragment;
import pers.zjc.sams.module.main.view.MainFragment;
import pers.zjc.sams.module.splash.SplashFragment;

public class MainActivity extends BaseActivity<MainComponent> {

    @Inject
    AppConfig appConfig;

    private FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fm = getSupportFragmentManager();
        appConfig.load();
        switchToSplashFragment();
    }

    public void switchToSplashFragment() {
        Fragment fragment = new SplashFragment();
        replace(R.id.container, fragment, fragment.getClass().getSimpleName());
    }

    public void switchToMainFragment(List<AttenceRecord> records) {
        Fragment fragment = new MainFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Const.Keys.KEY_ATTENCE_RECORDS,(ArrayList<AttenceRecord>)records);
        fragment.setArguments(bundle);
        replace(R.id.container, fragment, fragment.getClass().getSimpleName());

    }

    public void switchToLoginFragment() {
        Fragment fragment = new LoginFragment();
        replace(R.id.container, fragment, fragment.getClass().getSimpleName());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment instanceof BaseFragment) {
                ((BaseFragment)fragment).onNewIntent(intent);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected MainComponent createComponent() {
        return DaggerMainComponent.builder()
                                  .appComponent(SamsApplication.getComponent())
                                  .mainModule(new MainModule(this))
                                  .build();
    }

    @Override
    protected void inject(MainComponent component) {
        component.inject(this);
    }


    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 判断当前按键是返回键
        FragmentManager fragmentManager = this.getSupportFragmentManager();
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 获取当前回退栈中的Fragment个数
            int backStackEntryCount = fragmentManager.getBackStackEntryCount();
            if (backStackEntryCount > 0) {
                // 底部MainFragment、MyWorkContainerFragment、ContainerFragment、PersonCenterFragment 是没有addToBackStack
                fragmentManager.popBackStackImmediate();
            }
            else {
                moveTaskToBack(true);
            }
        }
        return true;
    }
}
