package pers.zjc.sams.module.sign.contract;

import com.zp.android.zlib.base.BaseModel;
import com.zp.android.zlib.base.BasePresenter;
import com.zp.android.zlib.base.BaseView;

import java.util.List;

import pers.zjc.sams.data.entity.SignRecord;

public interface SignListContract {
    interface Model extends BaseModel {
    }

    interface View extends BaseView<Presenter> {
        void showMessage(String message);

        void showEmpty();

        void hideEmpty();

        void setData(List<SignRecord> records);

        void finishRefresh();

        void showNetworkErro();

        void startRefresh();
    }

    interface Presenter extends BasePresenter<View, Model> {

        void loadHistory(int interval);
    }
}
