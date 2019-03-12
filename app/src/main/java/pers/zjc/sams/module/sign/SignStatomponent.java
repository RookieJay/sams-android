package pers.zjc.sams.module.sign;

import com.zp.android.zlib.di.BaseComponent;
import com.zp.android.zlib.di.FragmentScope;

import dagger.Component;
import pers.zjc.sams.app.AppComponent;
import pers.zjc.sams.module.sign.view.SignListFragment;
import pers.zjc.sams.module.sign.view.SignStatFragment;

@SuppressWarnings("WeakerAccess")
@FragmentScope
@Component(modules = SignStatModule.class, dependencies = AppComponent.class)
public interface SignStatomponent extends BaseComponent<SignStatFragment> {
}
