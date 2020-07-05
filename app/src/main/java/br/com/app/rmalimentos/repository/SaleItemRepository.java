package br.com.app.rmalimentos.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import br.com.app.rmalimentos.AppDataBase;
import br.com.app.rmalimentos.model.dao.SaleItemDAO;
import br.com.app.rmalimentos.model.entity.SaleItem;
import java.util.List;
import java.util.stream.Collectors;

public class SaleItemRepository {
  private SaleItemDAO saleItemDAO;
  private AppDataBase appDataBase;

  public SaleItemRepository(Application application) {
    this.appDataBase = AppDataBase.getDatabase(application);
    saleItemDAO = this.appDataBase.saleItemDAO();
  }

  public void deleteItem(final SaleItem saleItemToRemove) {
    this.saleItemDAO.delete(saleItemToRemove);
  }

  public LiveData<List<SaleItem>> findSaleItemBySale(final Long saleId) {
    return this.saleItemDAO.findItensBySale(saleId);
  }

  public void insertItens(final List<SaleItem> saleItemList) {
    this.saleItemDAO.insert(saleItemList.toArray(new SaleItem[saleItemList.size()]));
  }

  public void updateItens(final List<SaleItem> saleItemList) {
    this.saleItemDAO.update(saleItemList.toArray(new SaleItem[saleItemList.size()]));
  }
}
