package pers.zjc.sams.module.approval.presenter;

import android.util.Log;

import com.zp.android.zlib.base.RecyclerViewHolderHelper;
import com.zp.android.zlib.utils.StringUtils;

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
import pers.zjc.sams.module.leave.view.LeaveListAdapter;

public class ApprovalPresenter implements ApprovalContract.Presenter {

    private ApprovalContract.View view;
    @Inject
    ApprovalModel model;
    @Inject
    AppConfig appConfig;
    @Inject
    Executor executor;
    private LeaveListAdapter adapter;

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
                    Result<LeavesWrapper> result = model.getAllLeaves();
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

    @Override
    public void attend(int attenceStatus) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Result result = model.addtend(attenceStatus, appConfig.getUserId(), 2);
                if (result != null) {
                    view.showMessage(result.getMessage());
                    if (result.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                    }
                } else {
                    view.showNetworkErro();
                }
            }
        });

    }

    public void changeLeaveStatus(String id, int status, int position, RecyclerViewHolderHelper holder) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                Result result = null;
                switch (status) {
                    case 0:
                        break;
                    case 2:
                        result = model.pass(id);
                        break;
                    case 3:
                        result = model.refuse(id);
                        break;
                    default:
                        break;
                }
                if (result != null) {
                    view.showMessage(result.getMessage());
                    if (StringUtils.equals(result.getCode(), Const.HttpStatusCode.HttpStatus_200)) {
                        load();
                    }
                } else {
                    view.showNetworkErro();
                }
            }
        });
    }

    public void init(LeaveListAdapter adapter) {
        this.adapter = adapter;
    }
}
