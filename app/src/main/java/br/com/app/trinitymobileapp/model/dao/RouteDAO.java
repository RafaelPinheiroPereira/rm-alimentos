package br.com.app.trinitymobileapp.model.dao;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import br.com.app.trinitymobileapp.model.entity.Client;
import br.com.app.trinitymobileapp.model.entity.Route;

import java.util.List;

@Dao
public abstract class RouteDAO extends GenericDAO<Route> {

    @Query("select * from route ")
    public abstract LiveData<List<Route>> getAll();

    @Query("select * from route  where id = :id order by id")
    public abstract Route getById(Long id);

    private class OperationsAsyncTask extends AsyncTask<Route, Void, Void> {

        RouteDAO mAsyncTaskDao;

        OperationsAsyncTask(RouteDAO dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Route... routes) {
            return null;
        }
    }

    private class InsertAsyncTask extends OperationsAsyncTask {

        InsertAsyncTask(RouteDAO routeDAO) {
            super(routeDAO);
        }

        @Override
        protected Void doInBackground(Route... routes) {
            mAsyncTaskDao.insert(routes[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends OperationsAsyncTask {

        UpdateAsyncTask(RouteDAO routeDAO) {
            super(routeDAO);
        }

        @Override
        protected Void doInBackground(Route... routes) {
            mAsyncTaskDao.save(routes[0]);
            return null;
        }
    }

    @Override
    public void save(final Route obj) {
        new RouteDAO.InsertAsyncTask(this).execute(obj);
    }
}
