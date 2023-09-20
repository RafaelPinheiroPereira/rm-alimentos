package br.com.app.trinitymobileapp.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import br.com.app.trinitymobileapp.AppDataBase;
import br.com.app.trinitymobileapp.model.dao.ClientDAO;
import br.com.app.trinitymobileapp.model.entity.Client;
import br.com.app.trinitymobileapp.model.entity.Price;

import java.util.Date;
import java.util.List;

public class ClientRepository {

    private ClientDAO clientDAO;

    private AppDataBase appDataBase;

    public ClientRepository(Application application) {
        this.appDataBase = AppDataBase.getDatabase(application);
        this.clientDAO=this.appDataBase.clientDAO();
    }

    public LiveData<List<Client>> findNotPositived(final Date dateSale, final Long routeId) {

        return this.clientDAO.findNotPositivedClient(dateSale,routeId);
    }

    public LiveData<List<Client>> findPositivedClient(final Date dateSale, final Long routeId) {
        return this.clientDAO.findPositivedClient(dateSale,routeId);
    }

    public LiveData<List<Client>> getAll() {

        return clientDAO.getAll();
    }


        public void saveAll(final List<Client> clients) {
            clients.forEach(item->{
                if(this.clientDAO.getById(item.getId())==null){
                    this.clientDAO.save(item);
                }else{
                    this.clientDAO.update(item);
                }
            });


    }

    public  LiveData<List<Client>> getAllClientByRoute(Long routeId){
        return this.clientDAO.getAllClientByRoute(routeId);
    }
}
