package pers.zjc.sams.module.main;

import com.zp.android.zlib.di.ActivityScope;
import com.zp.android.zlib.di.BaseComponent;

import dagger.Component;
import pers.zjc.sams.app.AppComponent;

@SuppressWarnings("WeakerAccess")
@ActivityScope
@Component(dependencies = AppComponent.class, modules = MainModule.class)
public interface MainComponent extends BaseComponent<MainActivity> { }
