package br.com.app.trinitymobileapp.repository;

import android.app.Application;
import br.com.app.trinitymobileapp.AppDataBase;
import br.com.app.trinitymobileapp.model.dao.SaleItemDAO;
import br.com.app.trinitymobileapp.model.entity.SaleItem;
import java.util.List;

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

  public List<SaleItem> findSaleItemBySale(final Long saleId) {
    return this.saleItemDAO.findItensBySale(saleId);
  }

    public List<SaleItem> findItensToExport(final Long saleId) {
        return this.saleItemDAO.findItensToExport(saleId);
    }

  public void insertItens(final List<SaleItem> saleItemList) {
    this.saleItemDAO.insert(saleItemList.toArray(new SaleItem[saleItemList.size()]));
  }

  public void updateItens(final List<SaleItem> saleItemList) {
    this.saleItemDAO.update(saleItemList.toArray(new SaleItem[saleItemList.size()]));
  }
}
