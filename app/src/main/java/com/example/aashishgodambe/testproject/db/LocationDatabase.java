package com.example.aashishgodambe.testproject.db;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.aashishgodambe.testproject.AppExecutors;
import com.example.aashishgodambe.testproject.Utils;
import com.example.aashishgodambe.testproject.model.Location;

import java.util.List;

/**
 * Created by aashishgodambe on 1/14/19.
 */

@Database(entities = {Location.class}, version = 1, exportSchema = false)
public abstract class LocationDatabase extends RoomDatabase {

    private static LocationDatabase INSTANCE;

    public abstract LocationDao locationDao();

    public static final String DATABASE_NAME = "locations.db";

    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static LocationDatabase getInstance(final Context context, final AppExecutors executors){
        if(INSTANCE == null){
            synchronized (LocationDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = buildDatabase(context.getApplicationContext(), executors);
                    INSTANCE.updateDatabaseCreated(context.getApplicationContext());
                    Log.d("LocationDatabase", "Database created");
                }
            }

        }
        return INSTANCE;
    }

    private static LocationDatabase buildDatabase(final Context context, final AppExecutors executors){
        return  INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                LocationDatabase.class,DATABASE_NAME)
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                LocationDatabase database = LocationDatabase.getInstance(context,executors);
                                List<Location> locationList = Utils.addLocationItemsFromJson(context);
                                insertData(database,locationList);
                                database.setDatabaseCreated();
                            }
                        });

                    }
                })

                .build();
    }

    private static void insertData(final LocationDatabase database, final List<Location> locationList) {
        database.runInTransaction(new Runnable() {
            @Override
            public void run() {
                database.locationDao().insertList(locationList);
            }
        });
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }

    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated(){
        mIsDatabaseCreated.postValue(true);
    }
}
