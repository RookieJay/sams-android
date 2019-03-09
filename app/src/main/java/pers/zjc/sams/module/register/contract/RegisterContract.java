package pers.zjc.sams.module.register.contract;

import com.zp.android.zlib.base.BaseModel;
import com.zp.android.zlib.base.BasePresenter;
import com.zp.android.zlib.base.BaseView;

import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.User;

public interface RegisterContract {

    interface Model extends BaseModel {

        Result register(User user, String deviceId);
    }

    interface View extends BaseView<Presenter> {

        void fillImei(String s);

        void showMessage(String message);

        void showNetWorkErro();

        void back();
    }

    interface Presenter extends BasePresenter<View, Model> {

        void loadImei();

        void register(User user, String deviceId);
    }
}
