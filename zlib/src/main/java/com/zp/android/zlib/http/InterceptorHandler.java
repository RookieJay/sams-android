package com.zp.android.zlib.http;

import okhttp3.Request;

public interface InterceptorHandler {

    void handle(Request request, Object content);
}
