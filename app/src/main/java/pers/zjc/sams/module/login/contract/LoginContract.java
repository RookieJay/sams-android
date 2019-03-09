package pers.zjc.sams.module.login.contract;

import com.zp.android.zlib.base.BaseModel;
import com.zp.android.zlib.base.BasePresenter;
import com.zp.android.zlib.base.BaseView;

import java.util.List;

import pers.zjc.sams.data.entity.AttenceRecord;
import pers.zjc.sams.data.entity.Result;

public interface LoginContract {

    interface Model extends BaseModel {
        Result<?> login(String account, String pwd, String imei);
    }

    interface View extends BaseView<Presenter> {

        void showProgress();

        void closeProgress();

        void showMessage(String msg);

        void swithToMainFragment(List<AttenceRecord> records, String role);

        void showNetWorkErroMessage();

    }

    interface Presenter extends BasePresenter<View, Model> {

        void login(String account, String pwd, boolean isRemember);

        void init();
    }
}
