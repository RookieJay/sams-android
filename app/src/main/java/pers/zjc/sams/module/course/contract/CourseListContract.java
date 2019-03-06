package pers.zjc.sams.module.course.contract;

import com.zp.android.zlib.base.BaseModel;
import com.zp.android.zlib.base.BasePresenter;
import com.zp.android.zlib.base.BaseView;

import java.util.List;

import pers.zjc.sams.data.entity.Course;

public interface CourseListContract {

    interface Model extends BaseModel { }

    interface View extends BaseView<Presenter> {

        void startRefresh();

        void showEmpty();

        void hideEmpty();

        void showMessage(String 数据加载成功);

        void loadData(List<Course> courses);

        void finishRefresh();

        void showNetworkErro();
    }

    interface Presenter extends BasePresenter<View, Model> {

        void load();
    }
}
