package br.com.app.rmalimentos.repository;

import android.app.Application;
import androidx.lifecycle.LiveData;
import br.com.app.rmalimentos.AppDataBase;
import br.com.app.rmalimentos.model.dao.EmployeeDAO;
import br.com.app.rmalimentos.model.entity.Employee;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class EmployeeRepository {

  private EmployeeDAO employeeDAO;

  private LiveData<List<Employee>> listLiveData;

  private AppDataBase appDataBase;

  public EmployeeRepository(Application application) {
    appDataBase = AppDataBase.getDatabase(application);
    employeeDAO = appDataBase.employeeDAO();
  }

  public LiveData<List<Employee>> getAll() throws ExecutionException, InterruptedException {

    return employeeDAO.getAll();
  }

  public void save(final Employee employee) {
    employeeDAO.save(employee);
  }

  // TODO Implementar uma AsyncTask Generica de consultas
}
