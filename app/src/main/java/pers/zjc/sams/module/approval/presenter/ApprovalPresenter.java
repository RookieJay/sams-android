package pers.zjc.sams.module.approval.presenter;

import android.util.Log;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.datawrapper.LeavesWrapper;
import pers.zjc.sams.data.datawrapper.SignRecordsWrapper;
import pers.zjc.sams.data.entity.Leave;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.SignRecord;
import pers.zjc.sams.module.approval.contract.ApprovalContract;
import pers.zjc.sams.module.approval.model.ApprovalModel;
import pers.zjc.sams.module.leave.model.LeaveListModel;

public class ApprovalPresenter implements ApprovalContract.Presenter {

    private ApprovalContract.View view;
    @Inject
    ApprovalModel model;
    @Inject
    AppConfig appConfig;
    @Inject
    Executor executor;


    @Inject
    ApprovalPresenter(ApprovalContract.View view) {
        this.view = view;
    }


    @Override
    public void load() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                view.startRefresh();
                try {
                    Result<SignRecordsWrapper> result = model.getAllSign();
                    if (result != null ) {
                        if (result.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                            if (result.getData() != null) {
                                List<SignRecord> records = result.getData().getRecords();
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

    @Override
    public void attend(int attenceStatus) {
        Result result = model.addtend(attenceStatus, appConfig.getUserId(), 2);
    }
}
