package pers.zjc.sams.module.user.model;

import com.zp.android.zlib.http.HttpParam;

import javax.inject.Inject;

import pers.zjc.sams.data.datawrapper.StudentsWrapper;
import pers.zjc.sams.data.datawrapper.TeachersWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.Student;
import pers.zjc.sams.data.entity.Teacher;
import pers.zjc.sams.module.user.contract.UserManageContract;
import pers.zjc.sams.service.ApiService;

public class UserManageModel implements UserManageContract.Model {

    @Inject
    ApiService apiService;

    @Inject
    UserManageModel() {
    }


    public Result<StudentsWrapper> getStudents() {
        return apiService.getStudents();
    }

    public Result<TeachersWrapper> getTeachers() {
        return apiService.getTeachers();
    }

    public Result cancelStu(Student data) {
        HttpParam.Factory factory = new HttpParam.Factory()
                .add("stuId", String.valueOf(data.getStuId()))
                .add("sName", data.getName());
        return apiService.cancelStu(factory.create());
    }

    public Result delete(Teacher data) {
        HttpParam.Factory factory = new HttpParam.Factory()
                .add("id", String.valueOf(data.getId()))
                .add("tName", data.getName());
        return apiService.cancelTeac(factory.create());
    }

    public Result activate(Student data) {
        HttpParam.Factory factory = new HttpParam.Factory()
                .add("stuId", String.valueOf(data.getStuId()))
                .add("sName", data.getName());
        return apiService.activateStu(factory.create());
    }
}
