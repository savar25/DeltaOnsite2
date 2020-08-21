package com.example.deltaonsite2;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.util.Log;

public class app extends Application {


    private static final String TAG = "app";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: called");
        createNotificationChannnel();
    }
    public void createNotificationChannnel(){




    }
    }


