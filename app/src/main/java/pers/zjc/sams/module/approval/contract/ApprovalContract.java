package pers.zjc.sams.module.approval.contract;

import java.util.List;

import pers.zjc.sams.data.datawrapper.LeavesWrapper;
import pers.zjc.sams.data.entity.Leave;
import pers.zjc.sams.data.entity.Result;

public interface ApprovalContract {
    interface Model {

        Result<LeavesWrapper> getAllLeaves();
    }

    interface View {
        void showMessage(String message);

        void showEmpty();

        void hideEmpty();

        void setData(List<Leave> records);

        void finishRefresh();

        void showNetworkErro();

        void startRefresh();

        void notifyDataChanged(int status, int position);
    }

    interface Presenter {
        void load();

        void attend(int attenceStatus);
    }
}
