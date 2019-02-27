package pers.zjc.sams.module.sign;

import com.zp.android.zlib.di.BaseComponent;
import com.zp.android.zlib.di.FragmentScope;

import dagger.Component;
import pers.zjc.sams.app.AppComponent;
import pers.zjc.sams.module.sign.view.SignFragment;

@SuppressWarnings("WeakerAccess")
@FragmentScope
@Component(modules = SignModule.class, dependencies = AppComponent.class)
public interface SignComponent extends BaseComponent<SignFragment> {
}
