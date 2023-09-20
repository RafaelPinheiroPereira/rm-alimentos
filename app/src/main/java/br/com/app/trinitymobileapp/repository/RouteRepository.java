package br.com.app.trinitymobileapp.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import br.com.app.trinitymobileapp.AppDataBase;
import br.com.app.trinitymobileapp.model.dao.RouteDAO;
import br.com.app.trinitymobileapp.model.entity.Client;
import br.com.app.trinitymobileapp.model.entity.Route;
import java.util.List;

public class RouteRepository {

  private RouteDAO routeDAO;

  private AppDataBase appDataBase;

  public RouteRepository(Application application) {
    appDataBase = AppDataBase.getDatabase(application);
    routeDAO = appDataBase.routeDAO();
  }

  public LiveData<List<Route>> getAll() {
    return routeDAO.getAll();
  }


  public void saveAll(final List<Route> routes) {
    routes.forEach(item -> {
      if (this.routeDAO.getById(item.getId()) == null) {
        this.routeDAO.save(item);
      } else {
        this.routeDAO.update(item);
      }
    });

  }
}
