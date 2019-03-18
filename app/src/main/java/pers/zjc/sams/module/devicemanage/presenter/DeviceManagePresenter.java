package pers.zjc.sams.module.devicemanage.presenter;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.datawrapper.DevicesWrapper;
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
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Result<DevicesWrapper> result = model.getAllDevices();
                if (result != null) {
                    if (result.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                        List<Device> devices = result.getData().getDevices();
                        if (devices != null && devices.size() > 0) {
                            view.setData(devices);
                            view.hideEmpty();
                            view.showMessage("数据加载成功");
                        } else {
                            view.showEmpty();
                        }

                    }
                } else {
                    view.showNetworkErro();
                }
            }
        });
    }

    public void cancel(Device data) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Result result = model.updateStatus(data);
                if (result != null) {
                    view.showMessage(result.getMessage());
                } else {
                    view.showNetworkErro();
                }
            }
        });
    }

    public void activate(Device data) {
        cancel(data);
    }
}
