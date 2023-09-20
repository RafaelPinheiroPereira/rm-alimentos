package br.com.app.trinitymobileapp.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import br.com.app.trinitymobileapp.AppDataBase;
import br.com.app.trinitymobileapp.model.dao.PaymentDAO;
import br.com.app.trinitymobileapp.model.entity.Payment;
import br.com.app.trinitymobileapp.model.entity.Product;

import java.util.List;

public class PaymentRepository {
    private PaymentDAO paymentDAO;

    private LiveData<List<Payment>> listLiveData;

    private AppDataBase appDataBase;

    public PaymentRepository(Application application) {
        appDataBase = AppDataBase.getDatabase(application);
        paymentDAO = appDataBase.paymentDAO();
    }

    public List<Payment> getAll() {

        return paymentDAO.getAll();
    }



    public void saveAll(final List<Payment> payments) {
        payments.forEach(item -> {
            if (this.paymentDAO.getById(item.getId()) == null) {
                this.paymentDAO.save(item);
            } else {
                this.paymentDAO.update(item);
            }
        });

    }
}
