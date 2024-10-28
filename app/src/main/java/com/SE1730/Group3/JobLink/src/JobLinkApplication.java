package com.SE1730.Group3.JobLink.src;

import android.app.Application;

import com.SE1730.Group3.JobLink.src.domain.utilities.signalR.TransferHubService;
import com.SE1730.Group3.JobLink.src.presentation.DI.JobLinkEntryPoint;
import com.jakewharton.threetenabp.AndroidThreeTen;
import com.microsoft.signalr.HubConnection;

import javax.inject.Inject;

import dagger.hilt.android.EntryPointAccessors;
import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class JobLinkApplication extends Application {

    private TransferHubService transferHubService;

    @Override
    public void onCreate() {
        super.onCreate();
        AndroidThreeTen.init(this);

        HubConnection transferHubConnection = EntryPointAccessors.fromApplication(this, JobLinkEntryPoint.class).getTransferHubConnection();

        transferHubService = new TransferHubService(transferHubConnection, this);
    }
}

