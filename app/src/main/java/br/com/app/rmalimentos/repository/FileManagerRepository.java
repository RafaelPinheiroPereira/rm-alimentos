package br.com.app.rmalimentos.repository;

import br.com.app.rmalimentos.model.entity.Client;
import br.com.app.rmalimentos.model.entity.Employee;
import br.com.app.rmalimentos.model.entity.Payment;
import br.com.app.rmalimentos.model.entity.Price;
import br.com.app.rmalimentos.model.entity.Product;
import br.com.app.rmalimentos.model.entity.Route;
import br.com.app.rmalimentos.model.entity.Unity;
import br.com.app.rmalimentos.utils.FileManager;
import br.com.app.rmalimentos.utils.Singleton;
import java.io.IOException;
import java.util.List;

public class FileManagerRepository {

  ProductFileRepository productFileRepository;
  UnityFileRepository unityFileRepository;
  ClientFileRepository clientFileRepository;
  PaymentFileRepository paymentFileRepository;
  PriceFileRepository priceFileRepository;
  RouteFileRepository routeFileRepository;
  EmployeeFileRepository employeeFileRepository;

  Employee employee;

  FileManager fileManager;

  public FileManagerRepository() throws IllegalAccessException, InstantiationException {

    fileManager = Singleton.getInstance(FileManager.class);
  }

  public boolean containsAllFiles() {

    return fileManager.containsAllFiles();
  }

  public void createAppDirectory() {
    fileManager.createAppDirectory();
  }

  public void downloadFiles() throws IOException, IllegalAccessException, InstantiationException {

    productFileRepository = Singleton.getInstance(ProductFileRepository.class);
    unityFileRepository = Singleton.getInstance(UnityFileRepository.class);
    clientFileRepository = Singleton.getInstance(ClientFileRepository.class);
    paymentFileRepository = Singleton.getInstance(PaymentFileRepository.class);
    priceFileRepository = Singleton.getInstance(PriceFileRepository.class);
    routeFileRepository = Singleton.getInstance(RouteFileRepository.class);
    unityFileRepository = Singleton.getInstance(UnityFileRepository.class);

    readFiles();
  }

  private void readFiles() throws IOException, InstantiationException, IllegalAccessException {
    productFileRepository.readFile();
    unityFileRepository.readFile();
    clientFileRepository.readFile();
    paymentFileRepository.readFile();
    priceFileRepository.readFile();
    routeFileRepository.readFile();
    unityFileRepository.readFile();
  }

  public boolean fileExists(final String inputFile) {
    return fileManager.fileExists(inputFile);
  }

  public void readEmployeeFile()
      throws IllegalAccessException, InstantiationException, IOException {
    employeeFileRepository = Singleton.getInstance(EmployeeFileRepository.class);
    employeeFileRepository.readFile();
    this.setEmployee(employeeFileRepository.getEmployee());
  }

  public StringBuilder searchInexistsFilesNames() {
    return fileManager.searchInexistsFilesNames();
  }

  public Employee getEmployee() {
    return employee;
  }

  private void setEmployee(final Employee employee) {
    this.employee = employee;
  }

  public List<Route> getRoutes() {
    return routeFileRepository.getRoutes();
  }

  public List<Client> getClients() {
    return clientFileRepository.getClients();
  }

  public List<Product> getProducts() {
    return this.productFileRepository.getProducts();
  }

  public List<Unity> getUnities() {
    return this.unityFileRepository.getUnities();
  }

  public List<Payment> getPayments() {
    return this.paymentFileRepository.getPayments();
  }
  public List<Price> getPrices() {
    return this.priceFileRepository.getPrices();
  }
}
