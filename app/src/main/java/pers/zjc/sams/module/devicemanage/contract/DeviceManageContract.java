package pers.zjc.sams.module.devicemanage.contract;

import java.util.List;

import pers.zjc.sams.data.datawrapper.DevicesWrapper;
import pers.zjc.sams.data.entity.Device;
import pers.zjc.sams.data.entity.Result;

public interface DeviceManageContract {

    interface Model {

        Result<DevicesWrapper> getAllDevices();
    }

    interface View {
        void setData(List<Device> data);

        void startRefresh();

        void showEmpty();

        void hideEmpty();

        void showMessage(String msg);

        void finishRefresh();

        void showNetworkErro();
    }

    interface Presenter {

        void load();
    }
}
