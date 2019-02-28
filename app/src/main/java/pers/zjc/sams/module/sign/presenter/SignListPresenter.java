package pers.zjc.sams.module.sign.presenter;

import android.util.Log;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.datawrapper.SignRecordWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.SignRecord;
import pers.zjc.sams.module.sign.contract.SignListContract;
import pers.zjc.sams.module.sign.model.SignListModel;

public class SignListPresenter implements SignListContract.Presenter {

    @Inject
    Executor executor;
    @Inject
    SignListModel model;
    @Inject
    AppConfig appConfig;

    private SignListContract.View view;

    @Inject
    SignListPresenter(SignListContract.View view) {
        this.view = view;
    }

    @Override
    public void loadHistory(int interval) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                view.startRefresh();
                try {
                    SignRecord record = new SignRecord();
                    record.setStuId(Integer.valueOf(appConfig.getUserId()));
                    Result<SignRecordWrapper> result = model.getHistory(interval, record);
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
