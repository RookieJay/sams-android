package pers.zjc.sams.module.personcenter.contract;

import com.zp.android.zlib.base.BasePresenter;
import com.zp.android.zlib.base.BaseView;

public interface PersonCenterContract {

    interface Model {

    }

    interface View extends BaseView<Presenter> {

        void exit();
    }

    interface Presenter extends BasePresenter {

        void init();

        void exit();
    }
}
