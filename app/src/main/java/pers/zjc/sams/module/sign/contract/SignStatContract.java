package pers.zjc.sams.module.sign.contract;

import java.util.List;

import pers.zjc.sams.data.datawrapper.SignRecordsWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.SignRecord;

public interface SignStatContract {

    interface Model {

        Result<SignRecordsWrapper> getAllSign();
    }

    interface View {
        void showMessage(String message);

        void showEmpty();

        void hideEmpty();

        void setData(List<SignRecord> records);

        void finishRefresh();

        void showNetworkErro();

        void startRefresh();

        void showStats(int numSigned, int numUnsigned);
    }

    interface Presenter {
        void load();
    }
}
