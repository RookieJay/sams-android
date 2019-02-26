package com.zp.android.zlib.db;

import com.raizlabs.android.dbflow.config.FlowManager;
import com.raizlabs.android.dbflow.sql.language.From;
import com.raizlabs.android.dbflow.sql.language.OrderBy;
import com.raizlabs.android.dbflow.sql.language.SQLOperator;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.queriable.ListModelLoader;
import com.raizlabs.android.dbflow.sql.queriable.SingleModelLoader;
import com.raizlabs.android.dbflow.sql.queriable.StringQuery;
import com.raizlabs.android.dbflow.structure.ModelAdapter;
import com.raizlabs.android.dbflow.structure.database.AndroidDatabase;
import com.raizlabs.android.dbflow.structure.database.DatabaseWrapper;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({ "WeakerAccess", "unused", "UnusedReturnValue" })
public class BaseDao<T> {

    private Class<T> clazz;
    private String dbName;
    private DaoManager daoManager;

    public DaoManager getDaoManager() {
        return daoManager;
    }

    public void setDaoManager(DaoManager daoManager) {
        this.daoManager = daoManager;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    void setClazz(Class<T> clazz) {
        this.clazz = clazz;
    }

    public String getDbName() {
        return dbName;
    }

    void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public T querySingle(List<SQLOperator> operators) {
        From<T> from = SQLite.select().from(clazz);
        if (operators != null && operators.size() > 0) {
            SQLOperator[] ops = new SQLOperator[operators.size()];
            return from.where(operators.toArray(ops)).querySingle();
        }
        else {
            return from.querySingle();
        }
    }

    public List<T> queryList(List<SQLOperator> operators, List<OrderBy> orders) {
        From<T> from = SQLite.select().from(clazz);
        if (operators != null && operators.size() > 0) {
            SQLOperator[] ops = new SQLOperator[operators.size()];
            return from.where(operators.toArray(ops)).orderByAll(getOrderBy(orders)).queryList();
        }
        else {
            return from.orderByAll(getOrderBy(orders)).queryList();
        }
    }

    public long queryCount(List<SQLOperator> operators) {
        From<T> from = SQLite.selectCountOf().from(clazz);
        if (operators != null && operators.size() > 0) {
            SQLOperator[] ops = new SQLOperator[operators.size()];
            return from.where(operators.toArray(ops)).count();
        }
        else {
            return from.count();
        }
    }

    public List<T> queryBySQL(String sql, Object[] args) {
        if (args != null && args.length > 0) {
            List<T> result = new ArrayList<>();
            getListLoader().load(getDatabase().rawQuery(sql, parseArgs(args)), result);
            return result;
        }
        else {
            return new StringQuery<>(clazz, sql).queryList();
        }
    }

    public T queryBySQLSingle(String sql, Object[] args) {
        if (args != null && args.length > 0) {
            return getSingleLoader().load(getDatabase().rawQuery(sql, parseArgs(args)));
        }
        else {
            return new StringQuery<>(clazz, sql).querySingle();
        }
    }

    public T querySingleBySQL(String sql, Object[] args) {
        if (args != null && args.length > 0) {
            return getSingleLoader().load(getDatabase().rawQuery(sql, parseArgs(args)));
        }
        else {
            return new StringQuery<>(clazz, sql).querySingle();
        }
    }

    public boolean save(T data) {
        try {
            getModelAdapter().save(data);
            return true;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean save(List<T> data) {
        beginTransaction();
        try {
            for (T t : data) {
                if (!save(t)) {
                    throw new RuntimeException(this.getClass().getSimpleName() + " data save error.");
                }
            }
            setTransactionSuccessful();
            return true;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            endTransaction();
        }
    }

    public boolean delete(T data) {
        return getModelAdapter().delete(data);
    }

    public boolean delete(List<T> data) {
        beginTransaction();
        try {
            for (T t : data) {
                if (!delete(t)) {
                    throw new RuntimeException(this.getClass().getSimpleName() + " data delete error.");
                }
            }
            setTransactionSuccessful();
            return true;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            endTransaction();
        }
    }

    public boolean execSQL(String sql, Object[] objects) {
        try {
            ((AndroidDatabase)getDatabase()).getDatabase().execSQL(sql, objects);
            return true;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected DatabaseWrapper getDatabase() {
        return FlowManager.getWritableDatabase(dbName);
    }

    protected void beginTransaction() {
        getDatabase().beginTransaction();
    }

    protected void setTransactionSuccessful() {
        getDatabase().setTransactionSuccessful();
    }

    protected void endTransaction() {
        getDatabase().endTransaction();
    }

    ModelAdapter<T> getModelAdapter() {
        return FlowManager.getModelAdapter(clazz);
    }

    ListModelLoader<T> getListLoader() {
        return getModelAdapter().getNonCacheableListModelLoader();
    }

    SingleModelLoader<T> getSingleLoader() {
        return getModelAdapter().getNonCacheableSingleModelLoader();
    }

    List<OrderBy> getOrderBy(List<OrderBy> orders) {
        return orders == null ? new ArrayList<OrderBy>() : orders;
    }

    private String[] parseArgs(Object[] objects) {
        String[] args = new String[objects.length];
        for (int i = 0; i < objects.length; i++) {
            args[i] = objects[i].toString();
        }
        return args;
    }
}
