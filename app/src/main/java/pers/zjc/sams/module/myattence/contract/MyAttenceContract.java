package pers.zjc.sams.module.myattence.contract;

import com.zp.android.zlib.base.BaseModel;
import com.zp.android.zlib.base.BasePresenter;
import com.zp.android.zlib.base.BaseView;

import java.util.List;

import pers.zjc.sams.data.entity.AttenceRecord;
import pers.zjc.sams.module.main.contract.MainContract;

public interface MyAttenceContract {
    interface Model extends BaseModel {
    }

    interface View extends BaseView<Presenter> {
        void resetData(List<AttenceRecord> records);

        void startRefresh();

        void finishRefresh();
        
        void showMessage(String msg);

        void showNetworkErro();

        void showEmpty();
    }

    interface Presenter extends BasePresenter<MainContract.View, Model> {
        void load();
    }
}
