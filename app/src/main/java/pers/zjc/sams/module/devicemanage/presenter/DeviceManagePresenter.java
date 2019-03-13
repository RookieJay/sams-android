package pers.zjc.sams.module.devicemanage.presenter;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.data.entity.Device;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.module.devicemanage.contract.DeviceManageContract;
import pers.zjc.sams.module.devicemanage.model.DeviceManageModel;

public class DeviceManagePresenter implements DeviceManageContract.Presenter {

    private DeviceManageContract.View view;
    @Inject
    Executor executor;
    @Inject
    AppConfig appConfig;
    @Inject
    DeviceManageModel model;

    @Inject
    DeviceManagePresenter(DeviceManageContract.View view) {
        this.view = view;
    }

    @Override
    public void load() {
        Result<Device> result = model.getAllDevices();
        if (result != null) {

        }
    }
}
