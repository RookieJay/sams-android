package pers.zjc.sams.module.leave.presenter;

import android.util.Log;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.datawrapper.LeavesWrapper;
import pers.zjc.sams.data.entity.Leave;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.SignRecord;
import pers.zjc.sams.module.leave.contract.LeaveStatContract;
import pers.zjc.sams.module.leave.model.LeaveStatModel;

public class LeaveStatPresenter implements LeaveStatContract.Presenter {

    private LeaveStatContract.View view;
    @Inject
    AppConfig appConfig;
    @Inject
    Executor executor;
    @Inject
    LeaveStatModel model;

    @Inject
    LeaveStatPresenter(LeaveStatContract.View view) {
        this.view = view;
    }

    @Override
    public void loadData() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                view.startRefresh();
                try {
                    Result<LeavesWrapper> result = model.getStuAllLeaves();
                    if (result != null ) {
                        if (result.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                            if (result.getData() != null) {
                                List<Leave> records = result.getData().getRecords();
                                if (records.size() == 0) {
                                    view.showEmpty();
                                } else {
                                    view.hideEmpty();
                                    view.showMessage("数据加载成功");
                                    view.setData(records);
                                }
                                view.finishRefresh();
                            }
                        } else {

                            view.showMessage(result.getCode()+result.getMessage());
                        }
                    } else {
                        view.showNetworkErro();
                    }
                    view.finishRefresh();
                } catch (Exception e) {
                    e.printStackTrace();
                    view.finishRefresh();
                    Log.d("捕获异常", e.getMessage());
                    view.showMessage(e.getMessage());
                }
            }
        });
    }
}
