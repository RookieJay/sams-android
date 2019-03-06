package pers.zjc.sams.module.course.model;

import javax.inject.Inject;

import pers.zjc.sams.data.datawrapper.CoursesWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.module.course.contract.CourseListContract;
import pers.zjc.sams.service.ApiService;

public class CourseListModel implements CourseListContract.Model {

    @Inject
    ApiService apiService;

    @Inject
    CourseListModel() {
    }

    public Result<CoursesWrapper> getCourses() {
        return apiService.getCourses();
    }
}
