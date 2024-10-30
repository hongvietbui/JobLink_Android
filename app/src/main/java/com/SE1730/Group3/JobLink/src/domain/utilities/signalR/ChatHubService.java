package com.SE1730.Group3.JobLink.src.domain.utilities.signalR;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.SE1730.Group3.JobLink.src.data.models.all.TransactionDTO;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;
import com.squareup.moshi.Moshi;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChatHubService extends Service {

    private Moshi moshi;
    private HubConnection hubConnection;
    private String userId = "0";

    @Inject
    public ChatHubService(Moshi moshi) {
        this.hubConnection = HubConnectionBuilder.create("http://10.0.2.2:8080/hub/chat?userId=" + userId).build();
        this.moshi = moshi;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startHubConnection();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(hubConnection != null){
            hubConnection.stop();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startHubConnection();
        return START_STICKY;
    }

    private void startHubConnection() {
        this.hubConnection = HubConnectionBuilder.create("http://10.0.2.2:8080/hub/chat?userId=" + userId).build();

        if (hubConnection != null && hubConnection.getConnectionState() == HubConnectionState.DISCONNECTED) {

            hubConnection.start()
                    .doOnComplete(() -> Log.d("ChatHubService", "Connection started with userId: " + userId))
                    .doOnError(error -> Log.e("ChatHubService", "Error starting connection", error))
                    .subscribe();

            hubConnection.on("ReceiveMessage", (data) -> {

                var jsonAdapter = moshi.adapter(TransactionDTO.class);
                try{


                }catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.class);
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}