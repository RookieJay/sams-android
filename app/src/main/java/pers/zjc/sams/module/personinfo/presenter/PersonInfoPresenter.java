package pers.zjc.sams.module.personinfo.presenter;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.Student;
import pers.zjc.sams.data.entity.Teacher;
import pers.zjc.sams.module.personinfo.contract.PersonInfoContract;
import pers.zjc.sams.module.personinfo.model.PersonInfoModel;

public class PersonInfoPresenter implements PersonInfoContract.Presenter {

    private PersonInfoContract.View view;
    @Inject
    Executor executor;
    @Inject
    AppConfig appConfig;
    @Inject
    PersonInfoModel model;

    @Inject
    PersonInfoPresenter(PersonInfoContract.View view) {
        this.view = view;
    }


    @Override
    public void loadData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {

                switch (appConfig.getRole()) {
                    case "0":

                        break;
                    case "1":
                        Result<Student> stuResult = model.getStuInfo(appConfig.getUserId());
                        if (stuResult != null) {
                            view.showMessage(stuResult.getMessage());
                            if (stuResult.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                                view.fillStuData(stuResult.getData());
                            }
                        } else {
                            view.showNetWorkErro();
                        }
                        break;
                    case "2":
                        Result<Teacher> teacherResult = model.getTeacherInfo(appConfig.getUserId());
                        if (teacherResult != null) {
                            view.showMessage(teacherResult.getMessage());
                            if (teacherResult.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                                view.fillTeacherData(teacherResult.getData());
                            }
                        } else {
                            view.showNetWorkErro();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void commit(Student student) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Result result = model.updateStu(student);
                if (result != null) {
                    view.showMessage(result.getMessage());
                    if (result.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                        Intent intent = new Intent(Const.Actions.ACTION_REFRESH_PERSON_INFO);
                        Log.d("传递的userName", student.getName());
                        appConfig.setUserName(student.getName());
                        intent.putExtra("userName", student.getName());

                        SamsApplication.get().sendBroadcast(intent);
                        view.back();
                    }
                } else {
                    view.showNetWorkErro();
                }
            }
        });
    }

    @Override
    public void commit(Teacher teacher) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Result result = model.updateTeacher(teacher);
                if (result != null) {
                    view.showMessage(result.getMessage());
                    if (result.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                        Log.d("传递的userName", teacher.getName());
                        appConfig.setUserName(teacher.getName());
                        Intent intent = new Intent(Const.Actions.ACTION_REFRESH_PERSON_INFO);
                        intent.putExtra(Const.Keys.KEY_USER_NAME, teacher.getName());
                        SamsApplication.get().sendBroadcast(intent);
                        view.back();
                    }
                } else {
                    view.showNetWorkErro();
                }
            }
        });
    }


}
