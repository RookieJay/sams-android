package pers.zjc.sams.module.devicemanage;

import com.zp.android.zlib.di.BaseComponent;
import com.zp.android.zlib.di.FragmentScope;

import dagger.Component;
import pers.zjc.sams.app.AppComponent;
import pers.zjc.sams.module.devicemanage.view.DeviceManageFragment;

@FragmentScope
@Component(modules = DeviceManageModule.class, dependencies = AppComponent.class)
public interface DeviceManageComponent extends BaseComponent<DeviceManageFragment> { }
