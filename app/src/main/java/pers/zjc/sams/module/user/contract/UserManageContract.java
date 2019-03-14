package pers.zjc.sams.module.user.contract;

import java.util.List;

import pers.zjc.sams.data.entity.Student;

public interface UserManageContract {

    interface Model { }

    interface View {
        void showMessage(String message);

        void showEmpty();

        void hideEmpty();

        void setData(List<Student> records);

        void finishRefresh();

        void showNetworkErro();

        void startRefresh();
    }

    interface Presenter { }
}
