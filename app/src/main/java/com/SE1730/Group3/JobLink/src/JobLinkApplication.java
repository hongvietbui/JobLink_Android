package com.SE1730.Group3.JobLink.src;

import android.app.Application;

import com.jakewharton.threetenabp.AndroidThreeTen;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class JobLinkApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);
    }
}
