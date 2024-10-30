package com.SE1730.Group3.JobLink.src.domain.utilities.signalR;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.SE1730.Group3.JobLink.src.data.models.all.TransactionDTO;
import com.SE1730.Group3.JobLink.src.presentation.adapters.BigDecimalAdapter;
import com.SE1730.Group3.JobLink.src.presentation.adapters.UUIDJsonAdapter;
import com.SE1730.Group3.JobLink.src.presentation.fragments.TransferSuccessDialog;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class TransferHubService extends Service {
    private Moshi moshi;
    private HubConnection hubConnection;
    private String userId = "0";

    @Inject
    public TransferHubService(Moshi moshi) {
        this.hubConnection = HubConnectionBuilder.create("http://10.0.2.2:8080/hub/transfer?userId=" + userId).build();
        this.moshi = moshi;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startHubConnection();
    }

    private void startHubConnection() {
        this.hubConnection = HubConnectionBuilder.create("http://10.0.2.2:8080/hub/transfer?userId=" + userId).build();

        if (hubConnection != null && hubConnection.getConnectionState() == HubConnectionState.DISCONNECTED) {

            hubConnection.start()
                    .doOnComplete(() -> Log.d("TransferHubService", "Connection started with userId: " + userId))
                    .doOnError(error -> Log.e("TransferHubService", "Error starting connection", error))
                    .subscribe();

            hubConnection.on("ReceiveTransfer", (data) -> {

                var jsonAdapter = moshi.adapter(TransactionDTO.class);
                try{
                    TransactionDTO transactionDTO = jsonAdapter.fromJson(data);

                    //display successful message
                    Intent boardcastIntent = new Intent("SHOW_TRANSFER_SUCCESS_DIALOG");
                    boardcastIntent.putExtra("amount", transactionDTO.getAmount().toString());
                    LocalBroadcastManager.getInstance(this).sendBroadcast(boardcastIntent);

                }catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.class);
        }
    }

    public void updateUserIdAndReconnect(String newUserId) {
        if (hubConnection != null) {
            hubConnection.stop().doOnComplete(() -> {
                Log.d("TransferHubService", "Connection stopped for re-authentication");
                this.userId = newUserId;
                startHubConnection();
            }).subscribe(
                    () -> Log.d("TransferHubService", "HubConnection completed"),
                    throwable -> Log.e("TransferHubService", "Error during HubConnection", throwable)
            );
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startHubConnection();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (hubConnection != null) {
            hubConnection.stop();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

