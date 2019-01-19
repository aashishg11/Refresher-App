package com.example.aashishgodambe.testproject.db;

import android.arch.lifecycle.LiveData;
import android.arch.paging.DataSource;
import android.arch.paging.LivePagedListBuilder;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.aashishgodambe.testproject.model.Location;

import java.util.List;

/**
 * Created by aashishgodambe on 1/14/19.
 */
@Dao
public interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertList(List<Location> locationsList);

    @Query("SELECT * FROM Location")
    //LiveData<List<Location>> getAllLocations();
    public abstract DataSource.Factory<Integer, Location> getAllLocations();
    //public abstract LivePagedListBuilder<> getAllLocations();

}
