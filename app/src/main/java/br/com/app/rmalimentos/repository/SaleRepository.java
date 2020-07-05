package br.com.app.rmalimentos.repository;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import br.com.app.rmalimentos.AppDataBase;
import br.com.app.rmalimentos.model.dao.SaleDAO;
import br.com.app.rmalimentos.model.entity.Sale;
import br.com.app.rmalimentos.utils.AsyntaskResponse;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SaleRepository  {
    private SaleDAO saleDAO;
    private AppDataBase appDataBase;

    public SaleRepository(Application application) {
        this.appDataBase = AppDataBase.getDatabase(application);
        saleDAO=appDataBase.saleDAO();
    }

    public void createSale(Sale sale) {
       this.saleDAO.insert(sale);
    }

    public Long findLastId() {
       return  this.saleDAO.findLastId();
    }

    public LiveData<Sale> findSaleByDate(Long dateSale){

        return this.saleDAO.findSaleByDate(dateSale);
    }

    public LiveData<Sale> findSaleByDateAndClient( final String dateSale,final Long clientId) {
        return this.saleDAO.findSaleByDateAndClient(dateSale,clientId);
    }

    public void updateSale(final Sale sale) {
        this.saleDAO.update(sale);
    }
}
