package pers.zjc.sams.module.leave.model;

import android.util.Log;

import com.zp.android.zlib.http.HttpParam;

import javax.inject.Inject;

import pers.zjc.sams.data.datawrapper.LeaveWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.SignRecord;
import pers.zjc.sams.module.leave.contract.LeaveListContract;
import pers.zjc.sams.service.ApiService;

public class LeaveListModel implements LeaveListContract.Model {

    @Inject
    ApiService apiService;

    @Inject
    LeaveListModel(){}

    public Result<LeaveWrapper> getHistory(SignRecord record) {
        Log.d("getStuId", String.valueOf(record.getStuId()));
        HttpParam.Factory factory = new HttpParam.Factory()
                .add("stuId", String.valueOf(record.getStuId()));
        return apiService.leaveList( factory.create());
    }
}
