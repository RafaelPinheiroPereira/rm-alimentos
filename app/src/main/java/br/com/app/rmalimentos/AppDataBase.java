package br.com.app.rmalimentos;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import br.com.app.rmalimentos.model.entity.Employee;

@Database(entities = Employee.class, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    private static volatile AppDataBase mAppDataBaseInstance;

    static AppDataBase getDatabase(final Context context) {
        if (mAppDataBaseInstance == null) {
            synchronized (AppDataBase.class) {
                if (mAppDataBaseInstance == null) {
                    mAppDataBaseInstance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDataBase.class, "rm_alimentos_database")
                            .build();
                }
            }
        }
        return mAppDataBaseInstance;
    }
}
