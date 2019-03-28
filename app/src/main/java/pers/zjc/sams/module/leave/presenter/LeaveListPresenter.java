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
import pers.zjc.sams.module.leave.contract.LeaveListContract;
import pers.zjc.sams.module.leave.model.LeaveListModel;

public class LeaveListPresenter implements LeaveListContract.Presenter {

    private LeaveListContract.View view;
    @Inject
    LeaveListModel model;
    @Inject
    AppConfig appConfig;
    @Inject
    Executor executor;

    @Inject
    LeaveListPresenter(LeaveListContract.View view) {
        this.view = view;
    }




    @Override
    public void loadHistory() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                view.startRefresh();
                try {
                    SignRecord record = new SignRecord();
                    record.setStuId(Integer.valueOf(appConfig.getUserId()));
                    Result<LeavesWrapper> result = model.getHistory(record);
                    if (result != null ) {
                        if (result.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                            if (result.getData() != null) {
                                List<Leave> records = result.getData().getRecords();
                                if (records.size() == 0) {
                                    view.showEmpty();
                                } else {
                                    view.hideEmpty();
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
