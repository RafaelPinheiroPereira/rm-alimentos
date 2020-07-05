package br.com.app.rmalimentos.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import br.com.app.rmalimentos.AppDataBase;
import br.com.app.rmalimentos.model.dao.ProductDAO;
import br.com.app.rmalimentos.model.entity.Product;
import java.util.List;
import java.util.concurrent.ExecutionException;

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

    public LiveData<List<Product>> getAll() throws ExecutionException, InterruptedException {

        return productDAO.getAll();
    }

    public void saveAll(final List<Product> products) {

            this.productDAO.save(products.toArray(new Product[products.size()]));

    }
}
