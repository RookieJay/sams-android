package pers.zjc.sams.module.myattence;

import com.zp.android.zlib.di.BaseComponent;
import com.zp.android.zlib.di.FragmentScope;

import dagger.Component;
import pers.zjc.sams.app.AppComponent;
import pers.zjc.sams.module.myattence.view.MyAttenceFragment;

@SuppressWarnings("WeakerAccess")
@FragmentScope
@Component(modules = MyAttenceModule.class, dependencies = AppComponent.class)
public interface MyAttenceComponent extends BaseComponent<MyAttenceFragment> { }
