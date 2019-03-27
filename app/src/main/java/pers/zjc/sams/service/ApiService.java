package pers.zjc.sams.service;

import com.zp.android.zlib.http.HttpParam;
import com.zp.android.zlib.http.POST;
import com.zp.android.zlib.http.Param;
import com.zp.android.zlib.http.ParamMap;

import java.util.Map;

import pers.zjc.sams.data.datawrapper.AttenceRecordsWrapper;
import pers.zjc.sams.data.datawrapper.CoursesWrapper;
import pers.zjc.sams.data.datawrapper.DevicesWrapper;
import pers.zjc.sams.data.datawrapper.LeavesWrapper;
import pers.zjc.sams.data.datawrapper.SignRecordsWrapper;
import pers.zjc.sams.data.datawrapper.StudentsWrapper;
import pers.zjc.sams.data.datawrapper.TeachersWrapper;
import pers.zjc.sams.data.datawrapper.UserWrapper;
import pers.zjc.sams.data.entity.Result;
import pers.zjc.sams.data.entity.Student;
import pers.zjc.sams.data.entity.Teacher;

public interface ApiService {

    @POST("/api/mobile/login")
    Result<UserWrapper> login(@ParamMap HttpParam param);

    @POST("/test/1")
    Result<?> test();

    @POST("/api/mobile/attence/list/multiCond")
    Result<AttenceRecordsWrapper> getMultiCondRecord(@ParamMap HttpParam param);

    @POST("/api/mobile/leaves/askForLeave")
    Result askForLeave(@ParamMap HttpParam param);

    @POST("/api/mobile/leaves/all")
    Result<LeavesWrapper> allStuLeaves();

    @POST("/api/mobile/courses/today")
    Result<CoursesWrapper> getTodayCourses();

    @POST("/api/mobile/courses/all")
    Result<CoursesWrapper> getAllCourses();

    @POST("/api/mobile/courses/delete")
    Result deleteCourse(@ParamMap HttpParam httpParam);

    @POST("/api/mobile/sign")
    Result sign(@ParamMap HttpParam param);

    @POST("/api/mobile/sign/list")
    Result<SignRecordsWrapper> signList(@Param("interval") String interval, @ParamMap HttpParam param);

    @POST("/api/mobile/leaves/individual")
    Result<LeavesWrapper> leaveList(@ParamMap HttpParam param);

    @POST("/api/mobile/leaves/all")
    Result<LeavesWrapper> leaveListAll();

    @POST("/api/mobile/leaves/revoke")
    Result revoke(@ParamMap HttpParam param);

    @POST("/api/mobile/users/modify/pwd")
    Result modifyPwd(@ParamMap  HttpParam param);

    @POST("/api/mobile/users/register")
    Result register(@ParamMap HttpParam httpParam);

    @POST("/api/mobile/users/students/info")
    Result<Student> stuInfo(@ParamMap HttpParam httpParam);

    @POST("/api/mobile/users/teachers/info")
    Result<Teacher> teacherInfo(@ParamMap HttpParam httpParam);

    @POST("/api/mobile/users/students/info/modify")
    Result updateStudent(@ParamMap HttpParam httpParam);

    @POST("/api/mobile/users/teachers/info/modify")
    Result updateTeacher(@ParamMap HttpParam httpParam);

    @POST("/api/mobile/update")
    Result attend(@ParamMap HttpParam httpParam);

    @POST("/api/mobile/device/all")
    Result<DevicesWrapper> allDevices();

    @POST("/api/mobile/device/update")
    Result update(@ParamMap HttpParam httpParam);

    @POST("api/mobile/users/students/all")
    Result<StudentsWrapper> getStudents();

    @POST("api/mobile/users/teachers/all")
    Result<TeachersWrapper> getTeachers();

    @POST("api/mobile/courses/add")
    Result addCourse(@ParamMap HttpParam map);

    @POST("api/mobile/courses/update")
    Result updateCourse(@ParamMap HttpParam map);



}
