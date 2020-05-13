package br.com.app.rmalimentos;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import br.com.app.rmalimentos.model.converter.Converters;
import br.com.app.rmalimentos.model.dao.EmployeeDAO;
import br.com.app.rmalimentos.model.dao.RouteDAO;
import br.com.app.rmalimentos.model.entity.Client;
import br.com.app.rmalimentos.model.entity.Employee;
import br.com.app.rmalimentos.model.entity.Payment;
import br.com.app.rmalimentos.model.entity.Price;
import br.com.app.rmalimentos.model.entity.Product;
import br.com.app.rmalimentos.model.entity.Route;
import br.com.app.rmalimentos.model.entity.Sale;
import br.com.app.rmalimentos.model.entity.SaleItem;
import br.com.app.rmalimentos.model.entity.Unity;

@Database(
        entities = {
                Employee.class,
                Client.class,
                Payment.class,
                Price.class,
                Product.class,
                Route.class,
                Sale.class,
                SaleItem.class,
                Unity.class
        },
        version = 2)
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {

    private static volatile AppDataBase mAppDataBaseInstance;

    public abstract EmployeeDAO employeeDAO();

    public abstract RouteDAO routeDAO();

    public static AppDataBase getDatabase(final Context context) {
        if (mAppDataBaseInstance == null) {
            synchronized (AppDataBase.class) {
                if (mAppDataBaseInstance == null) {
                    mAppDataBaseInstance =
                            Room.databaseBuilder(
                                    context.getApplicationContext(), AppDataBase.class, "rm_alimentos_database")
                                    .fallbackToDestructiveMigration()
                                    .build();
                }
            }
        }
        return mAppDataBaseInstance;
    }
}
