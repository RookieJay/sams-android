package pers.zjc.sams.module.attence;

import com.zp.android.zlib.di.BaseComponent;
import com.zp.android.zlib.di.FragmentScope;

import dagger.Component;
import pers.zjc.sams.app.AppComponent;
import pers.zjc.sams.module.approval.ApprovalModule;
import pers.zjc.sams.module.approval.view.ApprovalFragment;
import pers.zjc.sams.module.approval.view.ApprovalStatFragment;
import pers.zjc.sams.module.attence.view.AttenceStateFragment;

@SuppressWarnings("WeakerAccess")
@FragmentScope
@Component(modules = AttenceStatModule.class, dependencies = AppComponent.class)
public interface AttenceStatComponent extends BaseComponent<AttenceStateFragment> {
}
