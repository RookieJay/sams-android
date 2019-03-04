package pers.zjc.sams.module.course.contract;

import com.zp.android.zlib.base.BaseModel;
import com.zp.android.zlib.base.BasePresenter;
import com.zp.android.zlib.base.BaseView;

public interface CourseListContract {

    interface Model extends BaseModel { }

    interface View extends BaseView<Presenter> { }

    interface Presenter extends BasePresenter<View, Model> { }
}
