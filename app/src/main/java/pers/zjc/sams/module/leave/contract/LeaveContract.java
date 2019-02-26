package pers.zjc.sams.module.leave.contract;

import java.util.List;

import pers.zjc.sams.data.entity.Course;
import pers.zjc.sams.data.entity.Leave;

public interface LeaveContract {
    interface Model {
    }

    interface View {

        void showCoursePopupWindow(List<Course> data);

        void showMessage(String msg);

        void showNetWorkErro();

        void showDialogProgress();

        void closeDialogProgress();

        void back();
    }

    interface Presenter {
        void commit(Leave commitLeave);

        void loadCourse();
    }
}
