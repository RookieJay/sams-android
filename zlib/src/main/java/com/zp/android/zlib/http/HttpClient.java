package com.zp.android.zlib.http;

import android.annotation.SuppressLint;

import com.google.gson.Gson;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

@SuppressLint("MissingPermission")
public class HttpClient {

    private static final MediaType MEDIA_TYPE_JSON = MediaType.parse("application/json; charset=utf-8");
    private final String url;
    private final OkHttpClient client;
    private final Gson gson;
    private final Interceptor interceptor;
    private final List<String> skips = new ArrayList<>();
    private final Map<Class<?>, Object> serviceMap = new HashMap<>();

    @SuppressWarnings("ConstantConditions")
    private final InvocationHandler invocationHandler = new InvocationHandler() {

        @Override
        public Object invoke(Object o, Method method, Object[] objects) {
            POST post = method.getAnnotation(POST.class);
            Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            if (post == null) {
                throw new RuntimeException("Error Method Annotations");
            }
            if (hasFile(parameterAnnotations)) {
                return requestFile(parameterAnnotations, method, objects, post.value());
            }
            else {
                return request(parameterAnnotations, method, objects, post.value());
            }
        }

        private Object requestFile(Annotation[][] parameterAnnotations, Method method, Object[] objects,
                                   String action) {
            Request.Builder rb = new Request.Builder().url(url + action);
            MultipartBody.Builder mb = new MultipartBody.Builder().setType(MultipartBody.FORM);
            for (int i = 0; i < parameterAnnotations.length; i++) {
                Annotation[] an = parameterAnnotations[i];
                if (an.length == 1) {
                    if (an[0] instanceof Header) {
                        rb.addHeader(((Header)an[0]).value(), objects[i].toString());
                    }
                    else if (an[0] instanceof Param) {
                        mb.addFormDataPart(((Param)an[0]).value(), objects[i] != null ? objects[i].toString() : "");
                    }
                    else if (an[0] instanceof ParamMap) {
                        if (objects[i] != null) {
                            if (objects[i] instanceof HttpParam) {
                                Map<String, String> hm = ((HttpParam)objects[i]).getParams();
                                for (String key : hm.keySet()) {
                                    mb.addFormDataPart(key, hm.get(key) != null ? hm.get(key) : "");
                                }
                            }
                            else {
                                throw new IllegalArgumentException("Error param");
                            }
                        }
                    }
                    else if (an[0] instanceof ParamFile) {
                        if (objects[i] instanceof File) {
                            File file = (File)objects[i];
                            mb.addFormDataPart(((ParamFile)an[0]).value(), file.getName(),
                                    RequestBody.create(null, file));
                        }
                        else {
                            throw new IllegalArgumentException("Error param");
                        }
                    }
                }
            }
            try {
                Request request = rb.post(mb.build()).build();
                if (interceptor != null && !interceptor.intercept() && !skips.contains(action)) {
                    if (interceptor.getHandlers() != null) {
                        for (InterceptorHandler handler : interceptor.getHandlers()) {
                            handler.handle(request, "");
                        }
                    }
                    return null;
                }
                Response response = client.newCall(request).execute();
                if (response != null && response.body() != null) {
                    return gson.fromJson(response.body().charStream(), method.getGenericReturnType());
                }
                else {
                    return null;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private Object request(Annotation[][] parameterAnnotations, Method method, Object[] objects, String action) {
            Request.Builder rb = new Request.Builder().url(url + action);
            Map<String, String> map = new LinkedHashMap<>();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                Annotation[] an = parameterAnnotations[i];
                if (an.length == 1) {
                    if (an[0] instanceof Header) {
                        rb.addHeader(((Header)an[0]).value(), objects[i].toString());
                    }
                    else if (an[0] instanceof Param) {
                        map.put(((Param)an[0]).value(), objects[i] != null ? objects[i].toString() : "");
                    }
                    else if (an[0] instanceof ParamMap) {
                        if (objects[i] != null) {
                            if (objects[i] instanceof HttpParam) {
                                map.putAll(((HttpParam)objects[i]).getParams());
                            }
                            else {
                                throw new IllegalArgumentException("Error param");
                            }
                        }
                    }
                }
            }
            try {
                String content = gson.toJson(map);
                Request request = rb.post(RequestBody.create(MEDIA_TYPE_JSON, content)).build();
                if (interceptor != null && !interceptor.intercept() && !skips.contains(action)) {
                    if (interceptor.getHandlers() != null) {
                        for (InterceptorHandler handler : interceptor.getHandlers()) {
                            handler.handle(request, content);
                        }
                    }
                    return null;
                }
                Response response = client.newCall(request).execute();
                ResponseBody body = response.body();
                if (response != null && body != null) {
//                    Log.d("--------->body", body.string());
                    return gson.fromJson(body.charStream(), method.getGenericReturnType());
                }
                else {
                    return null;
                }
            }
            catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        private boolean hasFile(Annotation[][] ans) {
            for (Annotation[] an : ans) {
                if (an[0] instanceof ParamFile) {
                    return true;
                }
            }
            return false;
        }
    };

    public HttpClient(OkHttpClient client, Gson gson, String url, Interceptor interceptor) {
        this.gson = gson;
        this.client = client;
        this.url = url;
        this.interceptor = interceptor;
    }

    public <T> T create(Class<T> service) {
        Object o = serviceMap.get(service);
        if (o == null) {
            o = Proxy.newProxyInstance(service.getClassLoader(), new Class<?>[] { service }, invocationHandler);
            serviceMap.put(service, o);
        }
        try {
            return service.cast(o);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void addSkips(String action) {
        skips.add(action);
    }
}
