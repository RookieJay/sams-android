package pers.zjc.sams.module.personcenter;

import com.zp.android.zlib.di.BaseComponent;
import com.zp.android.zlib.di.FragmentScope;

import dagger.Component;
import pers.zjc.sams.app.AppComponent;
import pers.zjc.sams.module.personcenter.view.PersonCenterFragment;

@SuppressWarnings("WeakerAccess")
@FragmentScope
@Component(modules = PersonCenterModule.class, dependencies = AppComponent.class)
public interface PersonCenterComponent extends BaseComponent<PersonCenterFragment> { }
