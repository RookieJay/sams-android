package pers.zjc.sams.module.main;

import pers.zjc.sams.app.AppConfig;
import com.zp.android.zlib.base.BaseActivity;
import com.zp.android.zlib.di.ActivityModule;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule extends ActivityModule {

    MainModule(BaseActivity activity) {
        super(activity);
    }

    @Provides
    Navigator provideNavigator(AppConfig appConfig) {
        return new Navigator(activity, appConfig);
    }
}
