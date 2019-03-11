package pers.zjc.sams.module.sign.model;

import android.util.Log;

import com.zp.android.zlib.http.HttpParam;

import javax.inject.Inject;

import pers.zjc.sams.data.datawrapper.SignRecordsWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.SignRecord;
import pers.zjc.sams.module.sign.contract.SignListContract;
import pers.zjc.sams.service.ApiService;

public class SignListModel implements SignListContract.Model {

    @Inject
    ApiService apiService;

    @Inject
    SignListModel(){}

    public Result<SignRecordsWrapper> getHistory(Integer interval, SignRecord record) {
        Log.d("getStuId", String.valueOf(record.getStuId()));
        HttpParam.Factory factory = new HttpParam.Factory()
                .add("stuId", String.valueOf(record.getStuId()));
        return apiService.signList(String.valueOf(interval), factory.create());
    }
}
