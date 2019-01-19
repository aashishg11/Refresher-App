package com.example.aashishgodambe.testproject.service;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.example.aashishgodambe.testproject.NotificationUtils;

/**
 * Created by aashishgodambe on 1/15/19.
 */

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class NotificationJobService extends JobService {

    /**
     * This is called by the system once it determines it is time to run the job.
     * @param jobParameters Contains the information about the job
     * @return Boolean indicating whether or not the job was offloaded to a separate thread.
     * In this case, it is false since the notification can be posted on the main thread.
     */
    @Override
    public boolean onStartJob(JobParameters jobParameters) {
        NotificationUtils.remindUserToOpenApp(this);
        Log.d("NotificationJobService","Job ran");

        return false;
    }

    /**
     * Called by the system when the job is running but the conditions are no longer met.
     * In this example it is never called since the job is not offloaded to a different thread.
     * @param jobParameters Contains the information about the job
     * @return Boolean indicating whether the job needs rescheduling
     */
    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }
}
