package pers.zjc.sams.module.face;

import com.zp.android.zlib.di.ActivityScope;
import com.zp.android.zlib.di.BaseComponent;

import dagger.Component;
import pers.zjc.sams.app.AppComponent;

@SuppressWarnings("WeakerAccess")
@ActivityScope
@Component(modules = FaceRegisterModule.class, dependencies = AppComponent.class)
public interface FaceRegisterComponent extends BaseComponent<FaceRegisterActivity> { }
