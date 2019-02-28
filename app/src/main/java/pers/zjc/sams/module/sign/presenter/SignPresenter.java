package pers.zjc.sams.module.sign.presenter;

import android.util.Log;
import com.zp.android.zlib.utils.TimeUtils;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.datawrapper.CoursesWrapper;
import pers.zjc.sams.data.datawrapper.SignRecordWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.SignRecord;
import pers.zjc.sams.module.sign.contract.SignContract;
import pers.zjc.sams.module.sign.model.SignModel;

public class SignPresenter implements SignContract.Presenter {

    private SignContract.View view;
    @Inject
    Executor executor;
    @Inject
    SignModel model;

    @Inject
    SignPresenter(SignContract.View view) {
        this.view = view;
    }

    @Override
    public void loadCourse() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Result<CoursesWrapper> result = model.getCourses();
                    if (result != null) {
                        if (result.getData() != null) {
                            view.showCoursePopupWindow(result.getData().getCourses());
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

    @Override
    public void sign(SignRecord record) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Result result = model.sign(record);
                    if (result != null) {
                        if (result.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                            view.showMessage(result.getMessage());
                            view.showSignedView();
                        } else if (result.getCode().equals(Const.HttpStatusCode.HttpStatus_403)) {  //临时增加用403表示已经提交
                            view.showSignedView();
                            view.showMessage(result.getMessage());
                        } else {
                            view.showMessage(result.getMessage());
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

    @Override
    public void getTime() {
    }


}
