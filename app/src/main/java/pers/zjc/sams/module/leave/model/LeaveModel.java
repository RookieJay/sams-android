package pers.zjc.sams.module.leave.model;

import com.zp.android.zlib.http.HttpParam;

import javax.inject.Inject;

import pers.zjc.sams.data.datawrapper.CoursesWrapper;
import pers.zjc.sams.data.entity.Leave;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.module.leave.contract.LeaveContract;
import pers.zjc.sams.service.ApiService;

public class LeaveModel implements LeaveContract.Model {

    @Inject
    ApiService apiService;

    @Inject
    LeaveModel() {
    }

    public Result commit(Leave commitLeave) {
        HttpParam.Factory factory = new HttpParam.Factory()
                .add("stuId", String.valueOf(commitLeave.getStuId()))
                .add("courseId", String.valueOf(commitLeave.getCourseId()))
                .add("beginTime", commitLeave.getBeginTime())
                .add("endTime", String.valueOf(commitLeave.getEndTime()))
                .add("reason", commitLeave.getReason());
        return apiService.askForLeave(factory.create());
    }


    public Result<CoursesWrapper> getCourses() {
        return apiService.getTodayCourses();
    }
}
