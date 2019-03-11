package pers.zjc.sams.module.leave.contract;

import java.util.List;

import pers.zjc.sams.data.datawrapper.LeavesWrapper;
import pers.zjc.sams.data.entity.Leave;
import pers.zjc.sams.data.entity.Result;

public interface LeaveStatContract {

    interface Model {

        Result<LeavesWrapper> getAllLeaves();

        Result<LeavesWrapper> getStuAllLeaves(String userId);
    }

    interface View {

        void showMessage(String message);

        void showEmpty();

        void hideEmpty();

        void setData(List<Leave> records);

        void finishRefresh();

        void showNetworkErro();

        void startRefresh();

        void showStats(int checkingNum, int revokeNum, int passNumb, int num);
    }

    interface Presenter {

        void loadData();

    }
}
