package pers.zjc.sams.module.course.model;

import com.zp.android.zlib.http.HttpParam;

import org.json.JSONObject;

import java.util.LinkedHashMap;
import java.util.Map;

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
        return apiService.updateCourse();
    }

    @Override
    public Result add(Course course) {

        Map<String, Object> map = new LinkedHashMap<>();
        map.put("course", course);
        return apiService.addCourse(map);
    }
}
