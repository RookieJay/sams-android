package pers.zjc.sams.module.leave;

import com.zp.android.zlib.di.BaseComponent;
import com.zp.android.zlib.di.FragmentScope;

import dagger.Component;
import pers.zjc.sams.app.AppComponent;
import pers.zjc.sams.module.leave.view.LeaveFragment;

@SuppressWarnings("WeakerAccess")
@FragmentScope
@Component(modules = LeaveModule.class, dependencies = AppComponent.class)
public interface LeaveComponent extends BaseComponent<LeaveFragment> {
}
