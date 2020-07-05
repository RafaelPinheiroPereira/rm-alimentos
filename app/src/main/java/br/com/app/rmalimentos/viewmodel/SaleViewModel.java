package br.com.app.rmalimentos.viewmodel;

import android.app.Application;
import android.content.Context;
import android.widget.ArrayAdapter;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import br.com.app.rmalimentos.model.entity.Client;
import br.com.app.rmalimentos.model.entity.Employee;
import br.com.app.rmalimentos.model.entity.Payment;
import br.com.app.rmalimentos.model.entity.Price;
import br.com.app.rmalimentos.model.entity.Product;
import br.com.app.rmalimentos.model.entity.Sale;
import br.com.app.rmalimentos.model.entity.SaleItem;
import br.com.app.rmalimentos.model.entity.Unity;
import br.com.app.rmalimentos.repository.PaymentRepository;
import br.com.app.rmalimentos.repository.PriceRepository;
import br.com.app.rmalimentos.repository.ProductRepository;
import br.com.app.rmalimentos.repository.SaleItemRepository;
import br.com.app.rmalimentos.repository.SaleRepository;
import br.com.app.rmalimentos.repository.UnityRepository;
import br.com.app.rmalimentos.tasks.InsertSaleItensTask;
import br.com.app.rmalimentos.utils.AsyntaskResponse;
import br.com.app.rmalimentos.utils.DateUtils;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class SaleViewModel extends AndroidViewModel   {

  /*Objetos da tela*/
  BigDecimal productQuantity;
  Product productSelected;
  Price priceProductSelected;
  Unity unitySelected;
  Payment paymentSelected;
  Client client;
  Employee employee;
  String dateSale;
  Sale sale;
  List<SaleItem> saleItems;

  Context context;




  /*Repositorios de acesso ao dados */
  PaymentRepository paymentRepository;
  SaleRepository saleRepository;
  ProductRepository productRepository;
  UnityRepository unityRepository;
  PriceRepository priceRepository;
  SaleItemRepository saleItemRepository;

  public SaleViewModel(@NonNull final Application application) {
    super(application);
    paymentRepository = new PaymentRepository(application);
    saleRepository = new SaleRepository(application);
    productRepository = new ProductRepository(application);
    unityRepository = new UnityRepository(application);
    priceRepository = new PriceRepository(application);
    saleItemRepository= new SaleItemRepository(application);
  }

  public void deleteSaleItem(final SaleItem saleItemToRemove) {
    this.saleItemRepository.deleteItem(saleItemToRemove);
  }

    public Product findProductById(final long productId) {
    return this.productRepository.findProductById(productId);
    }

    public LiveData<Sale> findSaleByDateAndClient() {

    return saleRepository.findSaleByDateAndClient(this.getDateSale(),this.getClient().getId());
  }

  public LiveData<List<SaleItem>> getAllSaleItens() {
    return this.saleItemRepository.findSaleItemBySale(getSale().getId());
  }

  public Client getClient() {
    return client;
  }

  public Long getLastId() {
    return this.saleRepository.findLastId();

  }

  public LiveData<List<Payment>> loadAllPaymentsType()
      throws ExecutionException, InterruptedException {
    return this.paymentRepository.getAll();
  }

  public LiveData<List<Product>> loadAllProducts() throws ExecutionException, InterruptedException {
    return this.productRepository.getAll();
  }

  public LiveData<List<Unity>> loadAllUnities() throws ExecutionException, InterruptedException {
    return this.unityRepository.getAll();
  }

  public LiveData<Price> loadPriceByUnitAndProduct() {
    return this.priceRepository.findPriceByUnitAndProduct(getProductSelected(), getUnitySelected());
  }

  public LiveData<List<Unity>> loadUnitiesByProduct(final Product product) {
    return this.unityRepository.findUnitiesByProduct(product);
  }

  public void insertSale() {
    Long lastId = this.getLastId();
    if (lastId != null) {
      this.getSale().setId(lastId + 1);
    } else {
      this.getSale().setId(1L);
    }

    this.getSale()
            .getSaleItemList()
            .forEach(
                    saleItem -> saleItem.setSaleId(this.getSale().getId()));
    this.saleItemRepository.insertItens(this.getSale().getSaleItemList());
    this.saleRepository.createSale(this.getSale());

  }


    public LiveData<Sale> searchSaleByDateAndClient() {

    return this.saleRepository.findSaleByDateAndClient(
       this.getDateSale(), this.getClient().getId());
  }

  public void setClient(final Client client) {
    this.client = client;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(final Employee employee) {
    this.employee = employee;
  }

  public String getDateSale() {
    return dateSale;
  }

  public void setDateSale(final String dateSale) {
    this.dateSale = dateSale;
  }

  public LiveData<Sale> findSaleByDate() throws ParseException {
    return saleRepository.findSaleByDate(
        DateUtils.converterStringParaDate(this.getDateSale()).getTime());
  }

  public BigDecimal getProductQuantity() {
    return productQuantity;
  }

  public void setProductQuantity(final BigDecimal productQuantity) {
    this.productQuantity = productQuantity;
  }

  public BigDecimal totalValueProduct() {
    return getProductQuantity().multiply(new BigDecimal(getPriceProductSelected().getValue()));
  }

  public  Double getAmount(){
    return new BigDecimal(getSaleItems().stream().mapToDouble(SaleItem::getTotalValue).sum())
            .setScale(2, BigDecimal.ROUND_HALF_DOWN)
            .doubleValue();
  }

  public Product getProductSelected() {
    return productSelected;
  }

  public void setProductSelected(final Product productSelected) {
    this.productSelected = productSelected;
  }

  public Price getPriceProductSelected() {
    return priceProductSelected;
  }

  public void setPriceProductSelected(final Price priceProductSelected) {
    this.priceProductSelected = priceProductSelected;
  }

  public Unity getUnitySelected() {
    return unitySelected;
  }

  public void setUnitySelected(final Unity unitySelected) {
    this.unitySelected = unitySelected;
  }

  public List<SaleItem> getSaleItems() {
    return this.saleItems;
  }

  public void insertItem(SaleItem saleItem) {
    this.getSaleItems().add(saleItem);
  }

  public void setSaleItems(final List<SaleItem> saleItems) {
    this.saleItems = saleItems;
  }

  public void updateSale() {
    this.getSale()
            .getSaleItemList()
            .forEach(
                    saleItem -> saleItem.setSaleId(this.getSale().getId()));

    List<SaleItem> itensToInsert=this.getSale().getSaleItemList().stream().filter(saleItem -> saleItem.getId()==null).collect(
            Collectors.toList());
    List<SaleItem> itensToUpdate=this.getSale().getSaleItemList().stream().filter(saleItem -> saleItem.getId()!=null).collect(
            Collectors.toList());
    this.saleItemRepository.insertItens(itensToInsert);
    this.saleItemRepository.updateItens(itensToUpdate);
    this.saleRepository.updateSale(this.getSale());
  }

  public Sale getSale() {
    return this.sale;
  }

  public void setSale(final Sale sale) {
    this.sale = sale;
  }

    public Payment getPaymentSelected() {
        return paymentSelected;
    }

    public void setPaymentSelected(final Payment paymentSelected) {
        this.paymentSelected = paymentSelected;
    }

  public Context getContext() {
    return context;
  }

  public void setContext(final Context context) {
    this.context = context;
  }

  public SaleItemRepository getSaleItemRepository() {
    return saleItemRepository;
  }


}