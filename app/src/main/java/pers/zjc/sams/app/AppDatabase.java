package pers.zjc.sams.app;

import com.raizlabs.android.dbflow.annotation.Database;

@SuppressWarnings({ "WeakerAccess", "unused" })
@Database(version = AppDatabase.VERSION)
public class AppDatabase {

    public static final String NAME = "sams";

    public static final int VERSION = 3;

    public static final String ENCRYPT_KEY = "my_db";
}