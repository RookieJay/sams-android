package pers.zjc.sams.module.login;

import com.zp.android.zlib.di.BaseComponent;
import com.zp.android.zlib.di.FragmentScope;

import dagger.Component;
import pers.zjc.sams.app.AppComponent;
import pers.zjc.sams.module.login.view.LoginFragment;

@SuppressWarnings("WeakerAccess")
@FragmentScope
@Component(modules = LoginModule.class, dependencies = AppComponent.class)
public interface LoginComponent extends BaseComponent<LoginFragment> {
}
