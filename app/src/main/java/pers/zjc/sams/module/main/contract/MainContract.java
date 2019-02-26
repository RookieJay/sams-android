package pers.zjc.sams.module.main.contract;

import com.zp.android.zlib.base.BasePresenter;
import com.zp.android.zlib.base.BaseView;

public interface MainContract {

    interface Model { }

    interface View extends BaseView<Presenter> { }

    interface Presenter extends BasePresenter { }
}
