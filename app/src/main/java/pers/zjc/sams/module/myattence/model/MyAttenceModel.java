package pers.zjc.sams.module.myattence.model;

import com.zp.android.zlib.http.HttpParam;

import javax.inject.Inject;

import pers.zjc.sams.app.AppConfig;
import pers.zjc.sams.data.datawrapper.AttenceRecordsWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.module.myattence.contract.MyAttenceContract;
import pers.zjc.sams.service.ApiService;

public class MyAttenceModel implements MyAttenceContract.Model {

    @Inject
    ApiService apiService;
    @Inject
    AppConfig appConfig;

    @Inject
    MyAttenceModel() {
    }

    public Result<AttenceRecordsWrapper> getRecords() {
        HttpParam.Factory factory = new HttpParam.Factory()
                .add("stuId", appConfig.getUserId());
        return apiService.getMultiCondRecord(factory.create());
    }

}
