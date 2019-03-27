package pers.zjc.sams.app;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zp.android.zlib.common.MainThread;
import com.zp.android.zlib.common.ThreadExecutor;
import com.zp.android.zlib.db.DaoManager;
import com.zp.android.zlib.http.HttpClient;

import java.util.Date;
import java.util.concurrent.Executor;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import pers.zjc.sams.service.ApiService;

@Module
public class AppModule {

    private Application application;
    private boolean isEncryptDb;

    public AppModule(Application app, boolean isEncy) {
        application = app;
        isEncryptDb = isEncy;
    }

    @Singleton
    @Provides
    Context provideContext() {
        return application;
    }

    @Singleton
    @Provides
    Boolean provideIsEncrypt() {
        return isEncryptDb;
    }

    @Singleton
    @Provides
    Executor provideExecutor() {
        return new ThreadExecutor();
    }

    @Singleton
    @Provides
    MainThread provideMainThread() {
        return new MainThread();
    }

    @Singleton
    @Provides
    AppConfig provideAppConfig() {
        return new AppConfig(application.getSharedPreferences("user_settings", Context.MODE_PRIVATE));
    }

    @Singleton
    @Provides
    ApiService provideApiService(HttpClient client) {
        return client.create(ApiService.class);
    }

    @Provides
    @Singleton
    Gson provideGson() {
        GsonBuilder builder = new GsonBuilder();
        builder.setDateFormat("yyyy-MM-dd HH:mm:ss");
        builder.registerTypeAdapter(Date.class, new DateTypeAdapter());
        return builder.create();
    }

    @Provides
    @Singleton
    DaoManager provideDaoManager() {
        DaoManager dm = new DaoManager(AppDatabase.NAME);
        return dm;
    }
}
