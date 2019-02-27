package pers.zjc.sams.module.sign.model;

import com.zp.android.zlib.http.HttpParam;

import javax.inject.Inject;

import pers.zjc.sams.data.datawrapper.CoursesWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.SignRecord;
import pers.zjc.sams.module.sign.contract.SignContract;
import pers.zjc.sams.service.ApiService;

public class SignModel implements SignContract.Model {

    @Inject
    SignModel() { }

    @Inject
    ApiService apiService;

    public Result<CoursesWrapper> getCourses() {
        return apiService.getCourses();
    }

    public Result sign(SignRecord record) {
        HttpParam.Factory factory = new HttpParam.Factory()
                .add("stuId", String.valueOf(record.getStuId()))
                .add("courseId", String.valueOf(record.getCourseId()))
                .add("location", record.getLocation())
                .add("signIp", record.getSignIp());
        return apiService.sign(factory.create());
    }
}
