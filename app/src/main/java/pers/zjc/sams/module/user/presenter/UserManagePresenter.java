package pers.zjc.sams.module.user.presenter;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.datawrapper.StudentsWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.Student;
import pers.zjc.sams.module.user.contract.UserManageContract;
import pers.zjc.sams.module.user.model.UserManageModel;

public class UserManagePresenter implements UserManageContract.Presenter {

    private UserManageContract.View view;
    @Inject
    UserManageModel model;
    @Inject
    Executor executor;
    @Inject
    AppConfig appConfig;

    @Inject
    UserManagePresenter(UserManageContract.View view) {
        this.view = view;
    }

    @Override
    public void init() {
        view.startRefresh();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Result<StudentsWrapper> result = model.getStudents();
                if (result != null) {
                    if (result.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                        List<Student> students = result.getData().getStudents();
                        if (students.size() > 0) {
                            view.setData(students);
                        } else {
                            view.showEmpty();
                        }
                        view.finishRefresh();
                    }
                } else {
                    view.showNetworkErro();
                    view.finishRefresh();
                }
            }
        });
    }
}
