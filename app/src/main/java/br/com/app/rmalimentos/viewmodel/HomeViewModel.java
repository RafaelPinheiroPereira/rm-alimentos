package br.com.app.rmalimentos.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import br.com.app.rmalimentos.model.entity.Route;
import br.com.app.rmalimentos.repository.RouteRepository;
import br.com.app.rmalimentos.utils.FileManager;
import br.com.app.rmalimentos.utils.Singleton;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class HomeViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();

    private RouteRepository routeRepository;

    FileManager fileManager;

    public HomeViewModel(@NonNull final Application application)
            throws IllegalAccessException, InstantiationException {
        super(application);
        routeRepository = new RouteRepository(application);
        fileManager = Singleton.getInstance(FileManager.class);
    }

    public void downloadFiles() {
        fileManager.downloadFiles();
    }

    public LiveData<List<Route>> getAllRoutes() throws ExecutionException, InterruptedException {
        return routeRepository.getAll();
    }

    public boolean containsAllFiles() {

        return fileManager.containsAllFiles();

    }

    public StringBuilder searchInexistsFilesNames() {
        return fileManager.searchInexistsFilesNames();
    }
}
