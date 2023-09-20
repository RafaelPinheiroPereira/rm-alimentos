package br.com.app.trinitymobileapp.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import br.com.app.trinitymobileapp.AppDataBase;
import br.com.app.trinitymobileapp.model.dao.ProductDAO;
import br.com.app.trinitymobileapp.model.entity.Product;
import br.com.app.trinitymobileapp.model.entity.Route;

import java.util.List;

public class ProductRepository {
    private ProductDAO productDAO;

    private LiveData<List<Product>> listLiveData;

    private AppDataBase appDataBase;

    public ProductRepository(Application application) {
        appDataBase = AppDataBase.getDatabase(application);
        productDAO = appDataBase.productDAO();
    }

    public Product findProductById(final long productId) {
        return this.productDAO.findProductById(productId);
    }

    public List<Product> getAll() {

        return productDAO.getAll();
    }


    public void saveAll(final List<Product> products) {
        products.forEach(item -> {
            if (this.productDAO.findProductById(item.getId()) == null) {
                this.productDAO.save(item);
            } else {
                this.productDAO.update(item);
            }
        });

    }
}
