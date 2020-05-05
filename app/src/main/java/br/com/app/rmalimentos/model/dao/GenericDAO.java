package br.com.app.rmalimentos.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.RawQuery;
import androidx.sqlite.db.SimpleSQLiteQuery;
import androidx.sqlite.db.SupportSQLiteQuery;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

@Dao
public abstract class GenericDAO<T extends Serializable> {

    @Delete
    public abstract void delete(T obj);

    public int deleteAll() {
        SimpleSQLiteQuery query = new SimpleSQLiteQuery("delete from " + getTableName());
        return doDeleteAll(query);
    }

    public T find(long id) {
        SimpleSQLiteQuery query =
                new SimpleSQLiteQuery(
                        "select * from " + getTableName() + " where deleteFlag = 0 and id = ?",
                        new Object[]{id});
        return doFind(query);
    }

    public List<T> findAllValid() {
        SimpleSQLiteQuery query =
                new SimpleSQLiteQuery(
                        "select * from " + getTableName() + " where deleteFlag = 0 order by sortKey");
        return doFindAllValid(query);
    }

    public String getTableName() {

        Class clazz =
                (Class)
                        ((ParameterizedType) getClass().getSuperclass().getGenericSuperclass())
                                .getActualTypeArguments()[0];

        return clazz.getSimpleName();
    }

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public abstract long[] insert(T... objs);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public abstract long insert(T obj);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long[] save(T... objs);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract long save(T obj);

    @RawQuery
    protected abstract int doDeleteAll(SupportSQLiteQuery query);

    @RawQuery
    protected abstract T doFind(SupportSQLiteQuery query);

    @RawQuery
    protected abstract List<T> doFindAllValid(SupportSQLiteQuery query);
}
