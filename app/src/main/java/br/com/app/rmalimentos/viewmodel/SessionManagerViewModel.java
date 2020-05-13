package br.com.app.rmalimentos.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import br.com.app.rmalimentos.model.entity.Employee;
import br.com.app.rmalimentos.repository.EmployeeRepository;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SessionManagerViewModel extends AndroidViewModel {

    private String TAG = this.getClass().getSimpleName();

    private EmployeeRepository employeeRepository;

    public SessionManagerViewModel(@NonNull final Application application)
            throws IllegalAccessException, InstantiationException {
        super(application);
        employeeRepository = new EmployeeRepository(application);
    }

    public LiveData<List<Employee>> checkedLogin() throws ExecutionException, InterruptedException {

        return employeeRepository.getAll();
    }
}
