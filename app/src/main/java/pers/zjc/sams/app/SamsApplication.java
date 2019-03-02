package pers.zjc.sams.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;

import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;
import com.zp.android.zlib.utils.CrashUtils;
import com.zp.android.zlib.utils.LogUtils;
import com.zp.android.zlib.utils.Utils;

import pers.zjc.sams.module.face.FaceDB;

public class SamsApplication extends Application {

    protected static AppComponent component;
    private static SamsApplication app;
    private final String TAG = this.getClass().toString();
    public FaceDB mFaceDB;
    public Uri mImage;

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
        mFaceDB = new FaceDB(this.getExternalCacheDir().getPath());
        mImage = null;
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

    public void setCaptureImage(Uri uri) {
        mImage = uri;
    }

    public Uri getCaptureImage() {
        return mImage;
    }

    /**
     * @param path
     * @return
     */
    public static Bitmap decodeImage(String path) {
        Bitmap res;
        try {
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

            BitmapFactory.Options op = new BitmapFactory.Options();
            op.inSampleSize = 1;
            op.inJustDecodeBounds = false;
            //op.inMutable = true;
            res = BitmapFactory.decodeFile(path, op);
            //rotate and scale.
            Matrix matrix = new Matrix();

            if (orientation == ExifInterface.ORIENTATION_ROTATE_90) {
                matrix.postRotate(90);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_180) {
                matrix.postRotate(180);
            } else if (orientation == ExifInterface.ORIENTATION_ROTATE_270) {
                matrix.postRotate(270);
            }

            Bitmap temp = Bitmap.createBitmap(res, 0, 0, res.getWidth(), res.getHeight(), matrix, true);
            Log.d("com.arcsoft", "check target Image:" + temp.getWidth() + "X" + temp.getHeight());

            if (!temp.equals(res)) {
                res.recycle();
            }
            return temp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
