package pers.zjc.sams.service;

import com.zp.android.zlib.http.HttpParam;
import com.zp.android.zlib.http.POST;
import com.zp.android.zlib.http.ParamMap;

import pers.zjc.sams.data.datawrapper.AttenceRecordsWrapper;
import pers.zjc.sams.data.datawrapper.CoursesWrapper;
import pers.zjc.sams.data.datawrapper.UserWrapper;
import pers.zjc.sams.data.entity.Leave;
import pers.zjc.sams.data.entity.Result;

public interface ApiService {

    @POST("/api/mobile/login")
    Result<UserWrapper> login(@ParamMap HttpParam param);

    @POST("/test/1")
    Result<?> test();

    @POST("/api/mobile/attence/list/multiCond")
    Result<AttenceRecordsWrapper> getMultiCondRecord(@ParamMap HttpParam param);

    @POST("/api/mobile/leaves/askForLeave")
    Result askForLeave(@ParamMap HttpParam param);

    @POST("/api/mobile/courses/list")
    Result<CoursesWrapper> getCourses();
}
