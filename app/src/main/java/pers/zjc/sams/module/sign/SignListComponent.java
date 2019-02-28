package pers.zjc.sams.module.sign;

import com.zp.android.zlib.di.BaseComponent;
import com.zp.android.zlib.di.FragmentScope;

import dagger.Component;
import pers.zjc.sams.app.AppComponent;
import pers.zjc.sams.module.sign.view.SignFragment;
import pers.zjc.sams.module.sign.view.SignListFragment;

@SuppressWarnings("WeakerAccess")
@FragmentScope
@Component(modules = SignListModule.class, dependencies = AppComponent.class)
public interface SignListComponent extends BaseComponent<SignListFragment> {
}
