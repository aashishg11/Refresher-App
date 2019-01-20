package com.example.aashishgodambe.testproject.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.paging.PagedList;
import android.os.AsyncTask;
import android.support.annotation.NonNull;

import com.example.aashishgodambe.testproject.AppExecutors;
import com.example.aashishgodambe.testproject.BasicApp;
import com.example.aashishgodambe.testproject.db.LocationDao;
import com.example.aashishgodambe.testproject.db.LocationDatabase;
import com.example.aashishgodambe.testproject.model.Location;
import com.example.aashishgodambe.testproject.repository.LocationRepository;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by aashishgodambe on 1/14/19.
 */

public class MainActivityViewModel extends AndroidViewModel {

    private final LocationRepository mRepository;

    public MainActivityViewModel(@NonNull Application application) {
        super(application);
        mRepository = ((BasicApp)application).getRepository();
    }

    public LiveData<PagedList<Location>> getAllLocations(){
        return mRepository.getAllLocations();
    }

}
