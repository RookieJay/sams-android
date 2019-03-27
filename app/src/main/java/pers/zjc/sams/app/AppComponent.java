package pers.zjc.sams.app;

import com.google.gson.Gson;
import com.zp.android.zlib.common.MainThread;
import com.zp.android.zlib.db.DaoManager;
import com.zp.android.zlib.http.HttpClient;

import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Component;
import okhttp3.OkHttpClient;
import pers.zjc.sams.service.ApiService;

@Singleton
@Component(modules = { AppModule.class, HttpModule.class })
public interface AppComponent {

    Executor getExecutor();

    MainThread getMainThread();

    AppConfig getAppConfig();

    HttpClient getHttpClient();

    OkHttpClient getOkHttpClient();

    Gson getGson();

    ApiService getApiService();

    DaoManager getDaoManager();

    NetworkInterceptor getNetworkInterceptor();
}
