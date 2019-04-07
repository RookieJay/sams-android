package pers.zjc.sams.module.user.contract;

import java.util.List;

import pers.zjc.sams.data.entity.Student;
import pers.zjc.sams.data.entity.Teacher;

public interface UserManageContract {

    interface Model { }

    interface View {
        void showMessage(String message);

        void showEmpty(boolean isStu);

        void hideEmpty();

        void setStuData(List<Student> records);

        void setTeaData(List<Teacher> teachers);

        void finishRefresh();

        void showNetworkErro();

        void startRefresh();

        void notifyStuDataChanged(int data, boolean isCancel);

        void notifyTeaDataChanged(int vPosition);
    }

    interface Presenter {

        void init();

        void update(Student data, int vPosition, boolean isCancel);

        void delete(Teacher data, int vPosition);
    }
}
