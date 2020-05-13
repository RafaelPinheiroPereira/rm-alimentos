package br.com.app.rmalimentos.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import br.com.app.rmalimentos.model.entity.Route;
import java.util.List;

@Dao
public abstract class RouteDAO extends GenericDAO<Route> {

    @Query("select * from route order by id")
    public abstract LiveData<List<Route>> getAll();


}
