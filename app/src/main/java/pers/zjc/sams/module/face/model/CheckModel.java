package pers.zjc.sams.module.face.model;

import com.zp.android.zlib.http.HttpParam;

import javax.inject.Inject;

import pers.zjc.sams.data.datawrapper.SignRecordsWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.SignRecord;
import pers.zjc.sams.module.face.contract.CheckContract;
import pers.zjc.sams.service.ApiService;

public class CheckModel implements CheckContract.Model {

    @Inject
    ApiService apiService;

    @Inject
    public CheckModel() {
    }

    @Override
    public Result<SignRecordsWrapper> getAllSign() {
        return apiService.signList("0", new HttpParam.Factory().create());
    }

    public Result update(int attenceStatus, SignRecord data) {
        HttpParam.Factory factory = new HttpParam.Factory()
                .add("stuId", String.valueOf(data.getStuId()))
                .add("courseId", String.valueOf(data.getCourseId()))
                .add("status", String.valueOf(attenceStatus));
        return apiService.updateAttence(factory.create());

    }
}
