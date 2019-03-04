package pers.zjc.sams.module.personcenter.contract;

import com.zp.android.zlib.base.BaseModel;
import com.zp.android.zlib.base.BasePresenter;
import com.zp.android.zlib.base.BaseView;

public interface ModifyPwdContract {

    interface Model extends BaseModel { }

    interface View extends BaseView<Presenter> {

        void showMessage(String message);

        void showNetWorkErro();

        void back();
    }

    interface Presenter extends BasePresenter<View, Model> {

        void commit(String oldPwd, String newPwd);
    }
}
