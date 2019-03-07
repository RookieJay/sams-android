package pers.zjc.sams.module.register;

import com.zp.android.zlib.di.BaseComponent;
import com.zp.android.zlib.di.FragmentScope;

import dagger.Component;
import pers.zjc.sams.app.AppComponent;
import pers.zjc.sams.module.register.view.RegisterFragment;
import pers.zjc.sams.module.sign.SignModule;
import pers.zjc.sams.module.sign.view.SignFragment;

@SuppressWarnings("WeakerAccess")
@FragmentScope
@Component(modules = RegisterModule.class, dependencies = AppComponent.class)
public interface RegisterComponent extends BaseComponent<RegisterFragment> {
}
