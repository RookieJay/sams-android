package pers.zjc.sams.module.user.presenter;

import com.zp.android.zlib.utils.StringUtils;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.datawrapper.StudentsWrapper;
import pers.zjc.sams.data.datawrapper.TeachersWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.Student;
import pers.zjc.sams.data.entity.Teacher;
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
                            view.setStuData(students);
                        } else {
                            view.showEmpty(true);
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

    public void load(boolean isStu) {
        if (isStu) {
            init();
        } else {
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    Result<TeachersWrapper> result = model.getTeachers();
                    if (result != null) {
                        if (result.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                            List<Teacher> teachers = result.getData().getTeachers();
                            if (teachers.size() > 0) {
                                view.setTeaData(teachers);
                            } else {
                                view.showEmpty(false);
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

    @Override
    public void update(Student data, int vPosition, boolean isCancel) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Result result;
                if (isCancel) {
                    result = model.cancelStu(data);
                } else {
                    result = model.activate(data);
                }
                if (null != result) {
                    view.showMessage(result.getMessage());
                    if (StringUtils.equals(result.getCode(), Const.HttpStatusCode.HttpStatus_200)) {
                        view.notifyStuDataChanged(vPosition, isCancel);
                    }
                } else {
                    view.showNetworkErro();
                }
            }
        });
    }

    @Override
    public void delete(Teacher data, int vPosition) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Result result = model.delete(data);
                if (null != result) {
                    view.showMessage(result.getMessage());
                    if (StringUtils.equals(result.getCode(), Const.HttpStatusCode.HttpStatus_200)) {
                        view.notifyTeaDataChanged(vPosition);
                    }
                } else {
                    view.showNetworkErro();
                }
            }
        });
    }
}
