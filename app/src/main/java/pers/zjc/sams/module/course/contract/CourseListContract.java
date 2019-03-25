package pers.zjc.sams.module.course.contract;

import com.zp.android.zlib.base.BaseModel;
import com.zp.android.zlib.base.BasePresenter;
import com.zp.android.zlib.base.BaseView;

import java.util.List;

import pers.zjc.sams.data.entity.Course;
import pers.zjc.sams.data.entity.Result;

public interface CourseListContract {

    interface Model extends BaseModel {
        Result removeCourse(Course mCourse);
    }

    interface View extends BaseView<Presenter> {

        void startRefresh();

        void showEmpty();

        void hideEmpty();

        void showMessage(String msg);

        void loadData(List<Course> courses);

        void finishRefresh();

        void showNetworkErro();

        void update(Course mCourse);

    }

    interface Presenter extends BasePresenter<View, Model> {

        void load();

        void deleteCourse(Course mCourse);
    }
}
