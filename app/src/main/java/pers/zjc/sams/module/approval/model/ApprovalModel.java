package pers.zjc.sams.module.approval.model;

import com.zp.android.zlib.http.HttpParam;

import javax.inject.Inject;

import pers.zjc.sams.data.datawrapper.SignRecordsWrapper;
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
    public Result<SignRecordsWrapper> getAllSign() {
        return apiService.signList("0", new HttpParam.Factory().create());
    }

    public Result addtend(int status, String userId, int courseId) {
        HttpParam.Factory factory = new HttpParam.Factory()
                .add("status", String.valueOf(status))
                .add("stuId", userId)
                .add("courseId", String.valueOf(courseId));
        return apiService.attend(factory.create());
    }
}
