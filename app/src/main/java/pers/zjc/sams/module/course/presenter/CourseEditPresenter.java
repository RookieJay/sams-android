package pers.zjc.sams.module.course.presenter;

import android.content.Intent;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.app.SamsApplication;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.entity.Course;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.module.course.contract.CourseEditContract;
import pers.zjc.sams.module.course.model.CourseEditModel;

public class CourseEditPresenter implements CourseEditContract.Presenter {

    private CourseEditContract.View view;
    @Inject
    Executor executor;
    @Inject
    AppConfig appConfig;
    @Inject
    CourseEditModel model;

    @Inject
    CourseEditPresenter(CourseEditContract.View view) {
        this.view = view;
    }

    public void commit(Course course) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Result result = null;
                if (view.getStartType() == 1) {
                    result = model.edit(course);
                }
                if (view.getStartType() == 2) {
                    result = model.add(course);
                }
                if (result != null) {
                    if (result.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                        SamsApplication.get().sendBroadcast(new Intent(Const.Actions.ACTION_REFRESH_COURSES));
                        view.back();
                    }
                    view.showMessage(result.getMessage());
                } else {
                    view.showError();
                }
            }
        });
    }
}
