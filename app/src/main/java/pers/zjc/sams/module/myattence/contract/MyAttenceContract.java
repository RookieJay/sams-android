package pers.zjc.sams.module.myattence.contract;

import com.zp.android.zlib.base.BaseModel;
import com.zp.android.zlib.base.BasePresenter;
import com.zp.android.zlib.base.BaseView;

import pers.zjc.sams.module.main.contract.MainContract;

public interface MyAttenceContract {
    interface Model extends BaseModel {
    }

    interface View extends BaseView<Presenter> {
    }

    interface Presenter extends BasePresenter<MainContract.View, Model> {
    }
}
