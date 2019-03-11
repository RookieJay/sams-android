package pers.zjc.sams.module.leave.model;

import com.zp.android.zlib.http.HttpParam;

import javax.inject.Inject;

import pers.zjc.sams.data.datawrapper.LeavesWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.module.leave.contract.LeaveStatContract;
import pers.zjc.sams.service.ApiService;

public class LeaveStatModel implements LeaveStatContract.Model {

    @Inject
    ApiService apiService;

    @Inject
    LeaveStatModel(){}

    @Override
    public Result<LeavesWrapper> getStuAllLeaves() {
        return apiService.allStuLeaves();
    }
}
