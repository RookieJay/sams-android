package pers.zjc.sams.module.center;

import com.zp.android.zlib.di.BaseComponent;
import com.zp.android.zlib.di.FragmentScope;

import dagger.Component;
import pers.zjc.sams.app.AppComponent;
import pers.zjc.sams.module.center.view.CenterFragment;
import pers.zjc.sams.module.myattence.view.MyAttenceFragment;

@SuppressWarnings("WeakerAccess")
@FragmentScope
@Component(modules = CenterModule.class, dependencies = AppComponent.class)
public interface CenterComponent extends BaseComponent<CenterFragment> { }
