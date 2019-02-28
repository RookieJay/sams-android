package pers.zjc.sams.module.center.contract;

import java.util.List;

import pers.zjc.sams.data.entity.FunctionInfo;

public interface CenterContract {
    interface Model {
    }

    interface View {
        void showData(List<FunctionInfo> functions);
    }

    interface Presenter {
        void loadCenterData();

        void loadSatisticsData();
    }
}
