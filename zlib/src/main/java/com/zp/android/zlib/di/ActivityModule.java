package com.zp.android.zlib.di;

import com.zp.android.zlib.base.BaseActivity;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    protected final BaseActivity activity;

    public ActivityModule(BaseActivity activity) {
        this.activity = activity;
    }

    @Provides
    BaseActivity provideActivity() {
        return activity;
    }
}
