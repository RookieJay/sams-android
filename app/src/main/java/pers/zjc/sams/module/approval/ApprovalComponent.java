package pers.zjc.sams.module.approval;

import com.zp.android.zlib.di.BaseComponent;
import com.zp.android.zlib.di.FragmentScope;

import dagger.Component;
import pers.zjc.sams.app.AppComponent;
import pers.zjc.sams.module.approval.view.ApprovalFragment;
import pers.zjc.sams.module.sign.view.SignFragment;

@SuppressWarnings("WeakerAccess")
@FragmentScope
@Component(modules = ApprovalModule.class, dependencies = AppComponent.class)
public interface ApprovalComponent extends BaseComponent<ApprovalFragment> {
}
