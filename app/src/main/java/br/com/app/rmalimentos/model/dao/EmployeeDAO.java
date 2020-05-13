package br.com.app.rmalimentos.model.dao;

import android.os.AsyncTask;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import br.com.app.rmalimentos.model.entity.Employee;
import java.util.List;

@Dao
public abstract class EmployeeDAO extends GenericDAO<Employee> {

    @Query("select * from employee order by id")
    public abstract LiveData<List<Employee>> getAll();

    private class OperationsAsyncTask extends AsyncTask<Employee, Void, Void> {

        EmployeeDAO mAsyncTaskDao;

        OperationsAsyncTask(EmployeeDAO dao) {
            this.mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(Employee... employees) {
            return null;
        }
    }

    private class InsertAsyncTask extends OperationsAsyncTask {

        InsertAsyncTask(EmployeeDAO employeeDAO) {
            super(employeeDAO);
        }

        @Override
        protected Void doInBackground(Employee... employees) {
            mAsyncTaskDao.insert(employees[0]);
            return null;
        }
    }

    @Override
    public void save(final Employee obj) {

        new InsertAsyncTask(this).execute(obj);

    }
}
