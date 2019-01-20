package com.example.aashishgodambe.testproject.views;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.aashishgodambe.testproject.BasicApp;
import com.example.aashishgodambe.testproject.R;
import com.example.aashishgodambe.testproject.RecyclerTouchListener;
import com.example.aashishgodambe.testproject.adapter.LocationAdapter;
import com.example.aashishgodambe.testproject.db.LocationDatabase;
import com.example.aashishgodambe.testproject.model.Location;
import com.example.aashishgodambe.testproject.service.NotificationJobService;
import com.example.aashishgodambe.testproject.viewmodel.MainActivityViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    List<Location> mLocationsList;
    RecyclerView recyclerView;
    LinearLayoutManager llm;
    private LocationAdapter adapter;
    private Button button;
    private Toast mToast;
    private Snackbar snackbar;
    private DrawerLayout mDrawerLayout;
    private LocationDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        button = findViewById(R.id.button);
        recyclerView = findViewById(R.id.recyclerView);
        mDrawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        adapter = new LocationAdapter();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked(true);
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Add code here to update the UI based on the item selected
                        // For example, swap UI fragments here

                        return true;
                    }
                });

        final MainActivityViewModel model = ViewModelProviders.of(this)
                .get(MainActivityViewModel.class);

        model.getAllLocations().observe(this, new Observer<PagedList<Location>>() {
            @Override
            public void onChanged(@Nullable PagedList<Location> locations) {
                Log.d("MainActivity","Size - "+locations.size());
                adapter.submitList(locations);
                mLocationsList = locations;

            }
        });
        recyclerView.setAdapter(adapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(this, recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                if (snackbar != null) snackbar.dismiss();
                snackbar = Snackbar.make(mDrawerLayout, mLocationsList.get(position).getAddress(), Snackbar.LENGTH_LONG);
                snackbar.show();

            }
        }));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // NotificationUtils.remindUserToOpenApp(MainActivity.this);
                scheduleJob();
            }
        });
    }

    public void scheduleJob(){
        ComponentName componentName = new ComponentName(this, NotificationJobService.class);
        JobInfo info = new JobInfo.Builder(111,componentName)
                .setRequiresCharging(false)
                .setPersisted(true)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPeriodic(15*60*1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if(resultCode == JobScheduler.RESULT_SUCCESS){
            Log.d("MainActivity","Job Scheduled");
        }else {
            Log.d("MainActivity","Job Cancelled");
        }

    }

    public void cancelJob(){
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(111);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.action_setings:
                Intent startSettingsActivity = new Intent(this,SettingsActivity.class);
                startActivity(startSettingsActivity);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflator = getMenuInflater();
        inflator.inflate(R.menu.location_menu,menu);
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals("show_notification")){
            boolean notificationSelected = sharedPreferences.getBoolean(key,true);
            hanndleNotifications(notificationSelected);
        }
    }

    private void hanndleNotifications(boolean notificationSelected) {
        if(notificationSelected){
            Log.d("MainActivity","Notifications are enabled.");
            scheduleJob();
        }else {
            Log.d("MainActivity","Notifications are disabled.");
            cancelJob();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }
}
