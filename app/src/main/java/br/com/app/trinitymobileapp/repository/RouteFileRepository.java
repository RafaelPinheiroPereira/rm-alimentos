package br.com.app.trinitymobileapp.repository;

import br.com.app.trinitymobileapp.model.entity.Route;
import br.com.app.trinitymobileapp.utils.Constants;
import br.com.app.trinitymobileapp.utils.RouteFile;
import br.com.app.trinitymobileapp.utils.Singleton;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class RouteFileRepository  implements  IFileRepository{

    List<Route> routes;

    RouteFile routeFile;

    public RouteFileRepository() throws IllegalAccessException, InstantiationException {
        routeFile= Singleton.getInstance(RouteFile.class);

    }

    @Override
    public void readFile() throws IOException, InstantiationException, IllegalAccessException {

        File file= routeFile.createFile(Constants.APP_DIRECTORY, Constants.INPUT_FILES[2]);
        routeFile.readFile(file);
        this.setRoutes(routeFile.getRoutes());

    }

    private void setRoutes(final List<Route> routes) {
        this.routes = routes;
    }

    public List<Route> getRoutes() {
        return routes;
    }
}
