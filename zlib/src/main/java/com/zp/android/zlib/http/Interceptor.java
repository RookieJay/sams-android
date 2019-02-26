package com.zp.android.zlib.http;

import java.util.List;

public interface Interceptor {

    // 执行拦截方法
    boolean intercept() throws Exception;

    // 拦截后的回调
    List<InterceptorHandler> getHandlers();
}
