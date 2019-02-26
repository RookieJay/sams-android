package pers.zjc.sams.app;

import android.util.Log;

import com.zp.android.zlib.http.Interceptor;
import com.zp.android.zlib.http.InterceptorHandler;
import com.zp.android.zlib.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NetworkInterceptor implements Interceptor {

    private static final String TAG = NetworkInterceptor.class.getSimpleName();

    private final List<InterceptorHandler> handlers = new ArrayList<>();

    @Override
    public boolean intercept() {
        boolean ping = NetworkUtils.isAvailableByPing();
        Log.d(TAG, "network is available: " + ping);
        return ping;
    }

    @Override
    public List<InterceptorHandler> getHandlers() {
        return Collections.unmodifiableList(handlers);
    }

    public void addInterceptorHandler(InterceptorHandler handler) {
        handlers.add(handler);
    }

    public void removeInterceptorHandler(InterceptorHandler handler) {
        handlers.remove(handler);
    }
}
