package pers.zjc.sams.module.sign.contract;

import com.zp.android.zlib.base.BaseModel;
import com.zp.android.zlib.base.BasePresenter;
import com.zp.android.zlib.base.BaseView;

import java.util.List;

import pers.zjc.sams.data.entity.Course;
import pers.zjc.sams.data.entity.SignRecord;

public interface SignContract {
    interface Model extends BaseModel {
    }

    interface View extends BaseView<Presenter> {

        void showCoursePopupWindow(List<Course> courses);

        void showMessage(String msg);

        void showNetWorkErro();

        void showSignedView();
    }

    interface Presenter extends BasePresenter<View, Model> {
        void loadCourse();

        void sign(SignRecord record);

        void getTime();

    }
}
