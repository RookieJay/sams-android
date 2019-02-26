package pers.zjc.sams.service;

import com.zp.android.zlib.http.HttpParam;
import com.zp.android.zlib.http.POST;
import com.zp.android.zlib.http.ParamMap;

import pers.zjc.sams.data.datawrapper.UserWrapper;
import pers.zjc.sams.data.entity.Result;

public interface ApiService {

    @POST("/api/mobile/login")
    Result<UserWrapper> login(@ParamMap HttpParam param);

    @POST("/test/1")
    Result<?> test();
}
