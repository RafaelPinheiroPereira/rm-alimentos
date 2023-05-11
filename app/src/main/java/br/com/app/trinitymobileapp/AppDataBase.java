package br.com.app.trinitymobileapp;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import br.com.app.trinitymobileapp.model.converter.Converters;
import br.com.app.trinitymobileapp.model.dao.ClientDAO;
import br.com.app.trinitymobileapp.model.dao.EmployeeDAO;
import br.com.app.trinitymobileapp.model.dao.PaymentDAO;
import br.com.app.trinitymobileapp.model.dao.PriceDAO;
import br.com.app.trinitymobileapp.model.dao.ProductDAO;
import br.com.app.trinitymobileapp.model.dao.RouteDAO;
import br.com.app.trinitymobileapp.model.dao.SaleDAO;
import br.com.app.trinitymobileapp.model.dao.SaleItemDAO;
import br.com.app.trinitymobileapp.model.dao.UnityDAO;
import br.com.app.trinitymobileapp.model.entity.Client;
import br.com.app.trinitymobileapp.model.entity.Employee;
import br.com.app.trinitymobileapp.model.entity.Payment;
import br.com.app.trinitymobileapp.model.entity.Price;
import br.com.app.trinitymobileapp.model.entity.Product;
import br.com.app.trinitymobileapp.model.entity.Route;
import br.com.app.trinitymobileapp.model.entity.Sale;
import br.com.app.trinitymobileapp.model.entity.SaleItem;
import br.com.app.trinitymobileapp.model.entity.Unity;

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
        version = 1)
@TypeConverters({Converters.class})
public abstract class AppDataBase extends RoomDatabase {

    private static volatile AppDataBase mAppDataBaseInstance;

    public abstract EmployeeDAO employeeDAO();

    public abstract RouteDAO routeDAO();
    public abstract ClientDAO clientDAO();
    public abstract ProductDAO productDAO();

    public abstract SaleDAO saleDAO();

    public abstract UnityDAO unityDAO();
    public abstract PaymentDAO paymentDAO();
    public abstract PriceDAO priceDAO();

    public abstract SaleItemDAO saleItemDAO();

    public static AppDataBase getDatabase(final Context context) {
        if (mAppDataBaseInstance == null) {
            synchronized (AppDataBase.class) {
                if (mAppDataBaseInstance == null) {
                    mAppDataBaseInstance =
                            Room.databaseBuilder(
                                    context.getApplicationContext(), AppDataBase.class, "rm_alimentos_database")
                                    .fallbackToDestructiveMigration()
                                    .allowMainThreadQueries()
                                    .build();
                }
            }
        }
        return mAppDataBaseInstance;
    }
}
