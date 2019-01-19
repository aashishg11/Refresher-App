package com.example.aashishgodambe.testproject.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;
import android.support.annotation.Nullable;

import com.example.aashishgodambe.testproject.db.LocationDatabase;
import com.example.aashishgodambe.testproject.model.Location;

import java.util.List;

/**
 * Created by aashishgodambe on 1/14/19.
 */

public class LocationRepository {

    private static LocationRepository sInstance;

    private final LocationDatabase mDatabase;

    private LiveData<PagedList<Location>> mObservableLocations;

    private LocationRepository(final LocationDatabase database) {
        mDatabase = database;
        PagedList.Config pagedListConfig = (new PagedList.Config.Builder()).setEnablePlaceholders(false)
                .setInitialLoadSizeHint(12)
                .setPageSize(12)
                .build();
        mObservableLocations = new LivePagedListBuilder<>(mDatabase.locationDao().getAllLocations(),pagedListConfig).build();

//        mObservableLocations.addSource(mDatabase.locationDao().getAllLocations(), new Observer<PagedList<Location>>() {
//            @Override
//            public void onChanged(@Nullable PagedList<Location> locations) {
//                if (mDatabase.getDatabaseCreated().getValue() != null) {
//                    mObservableLocations.postValue(locations);
//                }
//            }
//        });
    }

    public static LocationRepository getInstance(final LocationDatabase database) {
        if (sInstance == null) {
            synchronized (LocationRepository.class) {
                if (sInstance == null) {
                    sInstance = new LocationRepository(database);
                }
            }
        }
        return sInstance;
    }

    public LiveData<PagedList<Location>> getAllLocations() {
        return mObservableLocations;
    }

}
