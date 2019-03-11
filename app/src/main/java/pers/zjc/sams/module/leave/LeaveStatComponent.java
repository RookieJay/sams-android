package pers.zjc.sams.module.leave;

import com.zp.android.zlib.di.BaseComponent;
import com.zp.android.zlib.di.FragmentScope;

import dagger.Component;
import pers.zjc.sams.app.AppComponent;
import pers.zjc.sams.module.leave.view.LeaveStatFragment;

@SuppressWarnings("WeakerAccess")
@FragmentScope
@Component(modules = LeaveStatModule.class, dependencies = AppComponent.class)
public interface LeaveStatComponent extends BaseComponent<LeaveStatFragment> {
}
