package pers.zjc.sams.module.personinfo.model;

import com.zp.android.zlib.http.HttpParam;
import com.zp.android.zlib.utils.TimeUtils;

import javax.inject.Inject;

import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.Student;
import pers.zjc.sams.data.entity.Teacher;
import pers.zjc.sams.module.personinfo.contract.PersonInfoContract;
import pers.zjc.sams.service.ApiService;

public class PersonInfoModel implements PersonInfoContract.Model {

    @Inject
    ApiService apiService;

    @Inject
    PersonInfoModel() {
    }


    @Override
    public Result<Student> getStuInfo(String userId) {
        HttpParam.Factory factory = new HttpParam.Factory();
        factory.add("stuId", userId);
        return apiService.stuInfo(factory.create());
    }

    @Override
    public Result<Teacher> getTeacherInfo(String userId) {
        HttpParam.Factory factory = new HttpParam.Factory();
        factory.add("id", userId);
        return apiService.teacherInfo(factory.create());
    }

    @Override
    public Result updateStu(Student student) {
        HttpParam.Factory factory = new HttpParam.Factory()
                .add("stuId", String.valueOf(student.getStuId()))
                .add("sName", student.getName())
                .add("email", student.getEmail())
                .add("tel", student.getTel())
                .add("birthday", TimeUtils.date2String(student.getBirthday()));
        return apiService.updateStudent(factory.create());
    }

    @Override
    public Result updateTeacher(Teacher teacher) {
        HttpParam.Factory factory = new HttpParam.Factory()
                .add("id", String.valueOf(teacher.getId()))
                .add("tName", teacher.getName())
                .add("email", teacher.getEmail())
                .add("tel", teacher.getTel());
        return apiService.updateTeacher(factory.create());
    }
}
