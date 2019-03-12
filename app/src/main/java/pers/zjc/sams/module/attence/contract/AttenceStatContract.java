package pers.zjc.sams.module.attence.contract;

import java.util.List;

import pers.zjc.sams.data.entity.AttenceRecord;

public interface AttenceStatContract {

    interface Model { }

    interface View {
        void setData(List<AttenceRecord> records);

        void startRefresh();

        void finishRefresh();

        void showMessage(String msg);

        void showNetworkErro();

        void showEmpty();

        void hideEmpty();

        void setStats(int normal, int late, int leaving, int absent, int earlierLeave);
    }

    interface Presenter {

        void load();
    }
}
