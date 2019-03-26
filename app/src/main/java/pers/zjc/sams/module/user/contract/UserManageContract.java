package pers.zjc.sams.module.user.contract;

import java.util.List;

import pers.zjc.sams.data.entity.Student;
import pers.zjc.sams.data.entity.Teacher;

public interface UserManageContract {

    interface Model { }

    interface View {
        void showMessage(String message);

        void showEmpty();

        void hideEmpty();

        void setStuData(List<Student> records);

        void setTeaData(List<Teacher> teachers);

        void finishRefresh();

        void showNetworkErro();

        void startRefresh();


    }

    interface Presenter {

        void init();
    }
}
