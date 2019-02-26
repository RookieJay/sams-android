package com.zp.android.zlib.db;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class DaoManager {

    private final Map<Class<?>, BaseDao> daoMap = new HashMap<>();
    private String dbName;

    public DaoManager(String dbName) {
        this.dbName = dbName;
    }

    public <D> D getDao(Class<D> key) {

        BaseDao dao = daoMap.get(key);
        if (dao == null) {
            throw new RuntimeException(key.toString() + " maybe not register.");
        }
        try {
            return key.cast(dao);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    public <D extends BaseDao> void registerDao(Class<D> daoClazz) {
        try {
            Type genType = daoClazz.getGenericSuperclass();
            Class clazz = (Class)((ParameterizedType)genType).getActualTypeArguments()[0];
            BaseDao dao = daoClazz.newInstance();
            dao.setClazz(clazz);
            dao.setDbName(dbName);
            dao.setDaoManager(this);
            daoMap.put(daoClazz, dao);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
