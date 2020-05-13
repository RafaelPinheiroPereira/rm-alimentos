package br.com.app.rmalimentos.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import br.com.app.rmalimentos.model.entity.Employee;
import br.com.app.rmalimentos.repository.EmployeeRepository;
import br.com.app.rmalimentos.utils.Constants;
import br.com.app.rmalimentos.utils.FileManager;
import br.com.app.rmalimentos.utils.Singleton;
import java.io.IOException;

public class LoginViewModel extends AndroidViewModel {

    private Employee employee;

    private EmployeeRepository employeeRepository;

    FileManager fileManager;

    public LoginViewModel(@NonNull final Application application)
            throws InstantiationException, IllegalAccessException {

        super(application);

        employeeRepository = new EmployeeRepository(application);
        fileManager = Singleton.getInstance(FileManager.class);
    }

    public boolean employeeFileExists() {

        return fileManager.fileExists(Constants.INPUT_FILES[0]);
    }

    public void readEmployeeFile() throws IOException {

        fileManager.readEmployeeFile();
        Employee employee = fileManager.getEmployee();
        setEmployee(employee);
    }

    public void createAppDirectory() throws IOException {

        fileManager.createAppDirectory();
    }

    public Employee getEmployee() {
        return employee;
    }

    public void saveEmployee() {
        employeeRepository.save(this.getEmployee());
    }

    private void setEmployee(final Employee employee) {
        this.employee = employee;
    }
}
