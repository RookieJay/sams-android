package pers.zjc.sams.module.personinfo;

import com.zp.android.zlib.di.BaseComponent;
import com.zp.android.zlib.di.FragmentScope;

import dagger.Component;
import pers.zjc.sams.app.AppComponent;
import pers.zjc.sams.module.personcenter.PersonCenterModule;
import pers.zjc.sams.module.personcenter.view.PersonCenterFragment;
import pers.zjc.sams.module.personinfo.view.PersonInfoFragment;

@SuppressWarnings("WeakerAccess")
@FragmentScope
@Component(modules = PersonInfoModule.class, dependencies = AppComponent.class)
public interface PersonInfoComponent extends BaseComponent<PersonInfoFragment> { }
