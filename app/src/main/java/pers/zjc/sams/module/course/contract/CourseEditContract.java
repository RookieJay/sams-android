package pers.zjc.sams.module.course.contract;

import com.zp.android.zlib.base.BaseModel;
import com.zp.android.zlib.base.BasePresenter;
import com.zp.android.zlib.base.BaseView;

import pers.zjc.sams.data.entity.Course;
import pers.zjc.sams.data.entity.Result;

public interface CourseEditContract {

    interface Model extends BaseModel {

        Result edit(Course course);

        Result add(Course course);
    }

    interface View extends BaseView<Presenter> {

        int getStartType();

        void back();

        void showMessage(String message);

        void showError();
    }

    interface Presenter extends BasePresenter<View, Model> { }
}
