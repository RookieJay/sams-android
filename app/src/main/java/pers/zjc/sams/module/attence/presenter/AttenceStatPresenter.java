package pers.zjc.sams.module.attence.presenter;

import java.util.List;
import java.util.concurrent.Executor;

import javax.inject.Inject;

import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.common.Const;
import pers.zjc.sams.data.datawrapper.AttenceRecordsWrapper;
import pers.zjc.sams.data.entity.AttenceRecord;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.module.approval.model.ApprovalStatModel;
import pers.zjc.sams.module.attence.contract.AttenceStatContract;
import pers.zjc.sams.module.attence.model.AttenceStatModel;
import pers.zjc.sams.service.ApiService;

public class AttenceStatPresenter implements AttenceStatContract.Presenter {

    private AttenceStatContract.View view;
    @Inject
    AttenceStatModel model;
    @Inject
    ApiService apiService;
    @Inject
    Executor executor;
    @Inject
    AppConfig appConfig;

    @Inject
    AttenceStatPresenter(AttenceStatContract.View view) {
        this.view = view;
    }

    @Override
    public void load() {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                view.startRefresh();
                Result<AttenceRecordsWrapper> result = null;
                switch (appConfig.getRole()) {
                    case "0":
                        break;
                    case "1":
                        result = model.getStuRecords(appConfig.getUserId());
                        break;
                    case "2":
                        result = model.getAllRecords();
                        break;
                    default:
                        break;

                }
                if (result != null && result.getCode().equals(Const.HttpStatusCode.HttpStatus_200)) {
                    if (result.getData() != null) {
                        List<AttenceRecord> records = result.getData().getRecords();
                        if (records.size() == 0) {
                            view.showEmpty();
                        } else {
                            int normal = 0, late = 0, leaving = 0, absent = 0, earlierLeave = 0;
                            for (AttenceRecord record : records) {
                                switch (record.getStatus()) {
                                    case 0:
                                        normal++;
                                        break;
                                    case 1:
                                        late++;
                                        break;
                                    case 2:
                                        leaving++;
                                        break;
                                    case 3:
                                        absent++;
                                        break;
                                    case 4:
                                        earlierLeave++;
                                        break;
                                    default:
                                        break;
                                }
                                view.setStats(normal, late, leaving, absent, earlierLeave);
                            }
                            view.hideEmpty();
                            view.showMessage("数据加载成功");
                            view.setData(records);
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
