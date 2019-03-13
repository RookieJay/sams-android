package pers.zjc.sams.module.myattence.presenter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.datawrapper.AttenceRecordsWrapper;
import pers.zjc.sams.data.entity.AttenceRecord;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.module.myattence.contract.MyAttenceContract;
import pers.zjc.sams.module.myattence.model.MyAttenceModel;

public class MyAttencePresenter implements MyAttenceContract.Presenter {

    @Inject
    MyAttenceModel model;
    @Inject
    Executor executor;

    private MyAttenceContract.View view;

    @Inject
    MyAttencePresenter(MyAttenceContract.View view) {
        this.view = view;
    }


    @Override
    public void load() {
        view.startRefresh();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                view.startRefresh();
                Result<AttenceRecordsWrapper> result = model.getRecords();
                if (result != null && result.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                    if (result.getData() != null) {
                        List<AttenceRecord> records = result.getData().getRecords();
                        if (records.size() == 0) {
                            view.showEmpty();
                        } else {
                            view.hideEmpty();
                            view.showMessage("数据加载成功");
                            view.resetData(records);
                        }
                        view.finishRefresh();
                    }
                } else {
                    view.showNetworkErro();
                }
            }
        });
    }
}

