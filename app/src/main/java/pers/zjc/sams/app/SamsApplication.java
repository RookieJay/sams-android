package pers.zjc.sams.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;

import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.zp.android.zlib.utils.CrashUtils;
import com.zp.android.zlib.utils.LogUtils;
import com.zp.android.zlib.utils.Utils;

public class SamsApplication extends Application {

    protected static AppComponent component;
    private static SamsApplication app;

    public static AppComponent getComponent() {
        return component;
    }

    public static SamsApplication get() {
        return app;
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onCreate() {

        super.onCreate();
        app = this;
        //Flow db
        FlowConfig.Builder flowConfigBuilder = FlowConfig.builder(this);
        DatabaseConfig.Builder dbConfigBuilder = DatabaseConfig.builder(AppDatabase.class);
        flowConfigBuilder.addDatabaseConfig(dbConfigBuilder.databaseName(AppDatabase.NAME).build());
        FlowManager.init(flowConfigBuilder.build());
        //
        Stetho.initializeWithDefaults(this);
        Utils.init(this);
        String logPath = String.format("%s/%s/log", Environment.getExternalStorageDirectory().toString(),
                getPackageName());
        CrashUtils.init(logPath);
        LogUtils.getConfig().setDir(logPath).setFilePrefix("scmp");
        component = DaggerAppComponent.builder()
                                      .appModule(new AppModule(this, false))
                                      .httpModule(new HttpModule(this))
                                      .build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
    }

    @Override
    public Resources getResources() {
        Resources res = super.getResources();
        if (res != null && res.getConfiguration().fontScale != 1) {//非默认值
            Configuration newConfig = new Configuration();
            newConfig.setToDefaults();//设置默认
            res.updateConfiguration(newConfig, res.getDisplayMetrics());
        }
        return res;
    }
}
