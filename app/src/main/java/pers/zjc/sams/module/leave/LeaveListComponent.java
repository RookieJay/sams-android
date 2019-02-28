package pers.zjc.sams.module.leave;

import com.zp.android.zlib.di.BaseComponent;
import com.zp.android.zlib.di.FragmentScope;

import dagger.Component;
import pers.zjc.sams.app.AppComponent;
import pers.zjc.sams.module.leave.view.LeaveListFragment;

@SuppressWarnings("WeakerAccess")
@FragmentScope
@Component(modules = LeaveListModule.class, dependencies = AppComponent.class)
public interface LeaveListComponent extends BaseComponent<LeaveListFragment> {
}
