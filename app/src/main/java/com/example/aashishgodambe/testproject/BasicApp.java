package com.example.aashishgodambe.testproject;

import android.app.Application;

import com.example.aashishgodambe.testproject.db.LocationDatabase;
import com.example.aashishgodambe.testproject.repository.LocationRepository;

/**
 * Created by aashishgodambe on 1/14/19.
 */

public class BasicApp extends Application {

    private AppExecutors mAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();

        mAppExecutors = new AppExecutors();
    }

    public LocationDatabase getDatabase() {
        return LocationDatabase.getInstance(this, mAppExecutors);
    }

    public LocationRepository getRepository() {
        return LocationRepository.getInstance(getDatabase());
    }
}
