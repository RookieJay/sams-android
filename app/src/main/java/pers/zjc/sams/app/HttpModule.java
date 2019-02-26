package pers.zjc.sams.app;

import android.app.Application;
import android.content.Intent;
import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.zp.android.zlib.http.HttpClient;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import pers.zjc.sams.BuildConfig;
import pers.zjc.sams.common.Const;

@Module
public class HttpModule {

    private static final long CACHE_SIZE = 100 * 1024 * 1024;
    private static final long CONNECT_TIMEOUT = 60 * 1000 * 5;
    private static final long READ_TIMEOUT = 60 * 1000 * 5;
    private static final long WRITE_TIMEOUT = 60 * 1000 * 5;
    private Application application;

    public HttpModule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    HttpClient provideHttpClient(OkHttpClient client, Gson gson) {
        HttpClient hc = new HttpClient(client, gson, BuildConfig.BASE_URL, null);
        hc.addSkips("/api/mobile/system/ping");
        return hc;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttp() {
        String path = String.format("%s/%s/cache", Environment.getExternalStorageDirectory().toString(),
                application.getPackageName());
        Cache cache = new Cache(new File(path), CACHE_SIZE);
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
               .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
               .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
               .cookieJar(new HttpCookie())
               .cache(cache) //只对GET有效
               .authenticator(new Authenticator() {
                   @Override
                   public Request authenticate(Route route, Response response) {
                       application.sendBroadcast(new Intent(Const.Actions.ACTION_AUTH_ERROR));
                       Log.i("OkHttpClient", "authenticate: ==============================");
                       return null;
                   }
               });
        return builder.build();
    }

    @Provides
    @Singleton
    NetworkInterceptor provideNetworkInterceptor() {
        return new NetworkInterceptor();
    }
}
