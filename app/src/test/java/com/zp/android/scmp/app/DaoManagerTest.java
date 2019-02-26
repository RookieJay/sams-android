package com.zp.android.scmp.app;

import com.zp.android.scmp.data.dao.CheckResultDao;
import com.zp.android.zlib.db.DaoManager;

import org.junit.Test;

public class DaoManagerTest {

    @Test
    public void getDao() {
        DaoManager dm = new DaoManager(AppDatabase.NAME);
        dm.registerDao(CheckResultDao.class);
        CheckResultDao dao = dm.getDao(CheckResultDao.class);
        String a = "";
    }

    @Test
    public void test1() {

    }
}