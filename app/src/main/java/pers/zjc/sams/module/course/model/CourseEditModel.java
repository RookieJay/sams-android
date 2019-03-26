package pers.zjc.sams.module.course.model;

import com.zp.android.zlib.http.HttpParam;
import com.zp.android.zlib.utils.TimeUtils;

import javax.inject.Inject;

import pers.zjc.sams.data.entity.Course;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.module.course.contract.CourseEditContract;
import pers.zjc.sams.service.ApiService;

public class CourseEditModel implements CourseEditContract.Model {

    @Inject
    ApiService apiService;

    @Inject
    CourseEditModel(){}

    @Override
    public Result edit(Course course) {
        HttpParam param = createParam(course);
        return apiService.updateCourse(param);
    }

    @Override
    public Result add(Course course) {
        HttpParam param = createParam(course);
        return apiService.addCourse(param);
    }

    private HttpParam createParam(Course course) {
        HttpParam.Factory factory = new HttpParam.Factory()
                .add("id", String.valueOf(course.getId()))
                .add("name", course.getName())
                .add("classroom", course.getClassroom())
                .add("beginTime", TimeUtils.date2String(course.getBeginTime()))
                .add("endTime", TimeUtils.date2String(course.getEndTime()));
        return factory.create();
    }

}
