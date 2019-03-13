package pers.zjc.sams.module.devicemanage.contract;

import pers.zjc.sams.data.entity.Device;
import pers.zjc.sams.data.entity.Result;

public interface DeviceManageContract {

    interface Model {

        Result<Device> getAllDevices();
    }

    interface View { }

    interface Presenter {

        void load();
    }
}
