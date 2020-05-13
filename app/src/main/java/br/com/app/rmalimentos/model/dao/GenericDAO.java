package br.com.app.rmalimentos.model.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import java.io.Serializable;

@Dao
public abstract class GenericDAO<T extends Serializable> {

    @Delete
    public abstract void delete(T obj);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public abstract void insert(T... objs);

    @Insert(onConflict = OnConflictStrategy.FAIL)
    public abstract void insert(T obj);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void save(T... objs);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public abstract void save(T obj);
}
