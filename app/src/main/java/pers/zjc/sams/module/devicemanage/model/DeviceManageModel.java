package pers.zjc.sams.module.devicemanage.model;

import com.zp.android.zlib.http.HttpParam;

import javax.inject.Inject;

import pers.zjc.sams.data.datawrapper.DevicesWrapper;
import pers.zjc.sams.data.entity.Device;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.module.devicemanage.contract.DeviceManageContract;
import pers.zjc.sams.service.ApiService;

public class DeviceManageModel implements DeviceManageContract.Model {

    @Inject
    ApiService apiService;

    @Inject
    DeviceManageModel(){}

    @Override
    public Result<DevicesWrapper> getAllDevices() {
        return apiService.allDevices();
    }

    public Result updateStatus(Device data) {
        HttpParam.Factory factory = new HttpParam.Factory()
                .add("deviceId", data.getDeviceId())
                .add("deviceStatus", String.valueOf(data.getDeviceStatus()));
        return apiService.updateDevice(factory.create());
    }
}
