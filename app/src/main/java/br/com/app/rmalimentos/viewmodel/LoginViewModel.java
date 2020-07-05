package br.com.app.rmalimentos.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import br.com.app.rmalimentos.model.entity.Employee;
import br.com.app.rmalimentos.repository.EmployeeFileRepository;
import br.com.app.rmalimentos.repository.EmployeeRepository;
import br.com.app.rmalimentos.repository.FileManagerRepository;
import br.com.app.rmalimentos.utils.Constants;
import br.com.app.rmalimentos.utils.Singleton;
import java.io.IOException;

public class LoginViewModel extends AndroidViewModel {

    private Employee employee;

    private EmployeeRepository employeeRepository;



    FileManagerRepository fileManagerRepository;


    public LoginViewModel(@NonNull final Application application)
            throws InstantiationException, IllegalAccessException {

        super(application);

        employeeRepository = new EmployeeRepository(application);
        fileManagerRepository= Singleton.getInstance(FileManagerRepository.class);
    }

    public boolean employeeFileExists() {

        return fileManagerRepository.fileExists(Constants.INPUT_FILES[0]);
    }

    public void readEmployeeFile() throws IOException, InstantiationException, IllegalAccessException {

        fileManagerRepository.readEmployeeFile();
        setEmployee(fileManagerRepository.getEmployee());
    }

    public void createAppDirectory() throws IOException {

        fileManagerRepository.createAppDirectory();
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
