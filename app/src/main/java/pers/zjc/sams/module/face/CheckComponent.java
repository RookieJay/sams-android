package pers.zjc.sams.module.face;

import com.zp.android.zlib.di.BaseComponent;
import com.zp.android.zlib.di.FragmentScope;

import dagger.Component;
import pers.zjc.sams.app.AppComponent;
import pers.zjc.sams.module.attence.view.AttenceStateFragment;
import pers.zjc.sams.module.face.view.CheckFragment;

@SuppressWarnings("WeakerAccess")
@FragmentScope
@Component(modules = CheckModule.class, dependencies = AppComponent.class)
public interface CheckComponent extends BaseComponent<CheckFragment> {
}
