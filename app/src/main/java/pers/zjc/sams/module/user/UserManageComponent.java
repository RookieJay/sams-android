package pers.zjc.sams.module.user;

import com.zp.android.zlib.di.BaseComponent;
import com.zp.android.zlib.di.FragmentScope;

import dagger.Component;
import dagger.Module;
import pers.zjc.sams.app.AppComponent;
import pers.zjc.sams.module.user.view.UserManageFrament;

@FragmentScope
@Component(modules = UserManageModule.class, dependencies = AppComponent.class)
public interface UserManageComponent extends BaseComponent<UserManageFrament> { }
