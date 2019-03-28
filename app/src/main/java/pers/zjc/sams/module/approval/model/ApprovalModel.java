package pers.zjc.sams.module.approval.model;

import com.zp.android.zlib.http.HttpParam;

import javax.inject.Inject;

import pers.zjc.sams.data.datawrapper.LeavesWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.module.approval.contract.ApprovalContract;
import pers.zjc.sams.service.ApiService;

public class ApprovalModel implements ApprovalContract.Model {

    @Inject
    ApiService apiService;

    @Inject
    ApprovalModel() {
    }

    @Override
    public Result<LeavesWrapper> getAllLeaves() {
        return apiService.leaveListAll();
    }

    public Result addtend(int status, String userId, int courseId) {
        HttpParam.Factory factory = new HttpParam.Factory()
                .add("status", String.valueOf(status))
                .add("stuId", userId)
                .add("courseId", String.valueOf(courseId));
        return apiService.attend(factory.create());
    }

    public Result pass(String id) {
        HttpParam.Factory factory = new HttpParam.Factory()
                .add("id", id);
        return apiService.pass(factory.create());
    }

    public Result refuse(String id) {
        HttpParam.Factory factory = new HttpParam.Factory()
                .add("id", id);
        return apiService.refuse(factory.create());
    }
}
