package pers.zjc.sams.module.course.presenter;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.datawrapper.CoursesWrapper;
import pers.zjc.sams.data.entity.AttenceRecord;
import pers.zjc.sams.data.entity.Course;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.module.course.contract.CourseListContract;
import pers.zjc.sams.module.course.model.CourseListModel;

public class CourseListPresenter implements CourseListContract.Presenter {

    private CourseListContract.View view;
    @Inject
    AppConfig appConfig;
    @Inject
    CourseListModel model;
    @Inject
    Executor executor;

    @Inject
    CourseListPresenter(CourseListContract.View view) {
        this.view = view;
    }

    @Override
    public void load() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                view.startRefresh();
                Result<CoursesWrapper> result = model.getCourses();
                if (result != null && result.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                    if (result.getData() != null) {
                        List<Course> courses = result.getData().getCourses();
                        if (courses.size() == 0) {
                            view.showEmpty();
                        } else {
                            view.hideEmpty();
                            view.loadData(courses);
                        }
                        view.finishRefresh();
                    }
                } else {
                    view.showNetworkErro();
                }
            }
        });

    }

    @Override
    public void deleteCourse(Course mCourse) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Result result = model.removeCourse(mCourse);
                if (result != null) {
                    view.showMessage(result.getMessage());
                    if (result.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                        view.update(mCourse);
                    } else {
                        view.showMessage("删除失败");
                    }
                } else {
                    view.showNetworkErro();
                }
            }
        });

    }
}
