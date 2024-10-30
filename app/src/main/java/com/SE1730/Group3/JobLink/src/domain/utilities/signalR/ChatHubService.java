package com.SE1730.Group3.JobLink.src.domain.utilities.signalR;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

public class ChatHubService extends Service {

    private HubConnection hubConnection;
    private String userId = "0";

    public ChatHubService() {
        this.hubConnection = HubConnectionBuilder.create("http://10.0.2.2:8080/hub/chat?userId=" + userId).build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}