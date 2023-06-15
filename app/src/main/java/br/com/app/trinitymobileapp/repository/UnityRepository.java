package br.com.app.trinitymobileapp.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import br.com.app.trinitymobileapp.AppDataBase;
import br.com.app.trinitymobileapp.model.dao.UnityDAO;
import br.com.app.trinitymobileapp.model.entity.Product;
import br.com.app.trinitymobileapp.model.entity.Unity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class UnityRepository {


    private UnityDAO unityDAO;

    private LiveData<List<Unity>> listLiveData;

    private AppDataBase appDataBase;

    public UnityRepository(Application application) {
        appDataBase = AppDataBase.getDatabase(application);
        unityDAO = appDataBase.unityDAO();
    }

    public List<Unity> findUnitiesByProduct(final Product product) {

        return this.unityDAO.findUnitiesByProduct(product.getId());
    }

    public LiveData<List<Unity>> getAll() throws ExecutionException, InterruptedException {

        return unityDAO.getAll();
    }

    public void saveAll(final List<Unity> unitys) {
            unitys.forEach(item->{
                if (this.unityDAO.getById(item.getId()) == null) {
                    this.unityDAO.save(item);
                } else {
                    this.unityDAO.update(item);
                }
            });
    }
}
