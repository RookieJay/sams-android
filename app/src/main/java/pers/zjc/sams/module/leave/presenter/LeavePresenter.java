package pers.zjc.sams.module.leave.presenter;

import android.util.Log;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.datawrapper.CoursesWrapper;
import pers.zjc.sams.data.entity.Leave;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.module.leave.contract.LeaveContract;
import pers.zjc.sams.module.leave.model.LeaveModel;

public class LeavePresenter implements LeaveContract.Presenter {

    private LeaveContract.View view;
    @Inject
    LeaveModel model;
    @Inject
    Executor executor;

    @Inject
    LeavePresenter(LeaveContract.View view) {
        this.view = view;
    }

    @Override
    public void commit(Leave commitLeave) {
        view.showDialogProgress();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Result result = model.commit(commitLeave);
                    if (result != null) {
                        if (result.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                            view.closeDialogProgress();
                            view.showMessage("提交成功");
                            view.back();
                        } else {
                            view.showMessage(result.getMessage());
                        }
                    } else {
                        view.closeDialogProgress();
                        view.showNetWorkErro();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    view.closeDialogProgress();
                    Log.d("捕获异常", e.getMessage());
                    view.showMessage(e.getMessage());
                }
            }
        });

    }

    @Override
    public void loadCourse() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Result<CoursesWrapper> result = model.getCourses();
                    if (result != null) {
                        if (result.getData() != null && result.getData().getCourses() != null) {
                            view.showCoursePopupWindow(result.getData().getCourses());
                        } else {
                            view.showMessage("当前暂无课程");
                        }
                    } else {
                        view.showNetWorkErro();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("捕获异常", e.getMessage());
                    view.showMessage(e.getMessage());
                }

            }
        });
    }
}
