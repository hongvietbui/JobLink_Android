package com.SE1730.Group3.JobLink.src.domain.utilities.signalR;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.SE1730.Group3.JobLink.src.data.models.all.TransactionDTO;
import com.SE1730.Group3.JobLink.src.domain.dao.IMessageDAO;
import com.SE1730.Group3.JobLink.src.domain.entities.Message;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;
import com.squareup.moshi.Moshi;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ChatHubService extends Service {

    private static ChatHubService instance;
    private Moshi moshi;
    private HubConnection hubConnection;
    private String userId = "0";
    private IMessageDAO messageDAO;

    public ChatHubService() {
        instance = this;
    }
    @Inject
    public ChatHubService(Moshi moshi, IMessageDAO messageDAO) {
        this.moshi = moshi;
        this.messageDAO = messageDAO;
        this.hubConnection = HubConnectionBuilder.create("http://10.0.2.2:8080/hub/chat?userId=" + userId).build();
        instance = this;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startHubConnection();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (hubConnection != null) {
            hubConnection.stop();
        }
        instance = null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        startHubConnection();
        return START_STICKY;
    }

    private void startHubConnection() {
        if (hubConnection != null && hubConnection.getConnectionState() == HubConnectionState.DISCONNECTED) {
            hubConnection.start()
                    .doOnComplete(() -> Log.d("ChatHubService", "Connection started with userId: " + userId))
                    .doOnError(error -> Log.e("ChatHubService", "Error starting connection", error))
                    .subscribe();

            hubConnection.on("ReceiveMessage", (data) -> {
                var jsonAdapter = moshi.adapter(Message.class);
                try {
                    Message receivedMessage = jsonAdapter.fromJson(data);
                    if (receivedMessage != null) {
                        displayMessage(receivedMessage);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, String.class);
        }
    }

    public static void sendMessage(Message message) {
        if (instance != null) {
            instance.saveMessageToDatabase(message);
            try {
                instance.hubConnection.send("SendMessage", message);
                instance.displayMessage(message);
            } catch (Exception e) {
                Log.e("ChatHubService", "Error sending message", e);
                instance.deleteMessageFromDatabase(message);
            }
        } else {
            Log.e("ChatHubService", "Service not running, cannot send message");
        }
    }

    private void saveMessageToDatabase(Message message) {
        new Thread(() -> messageDAO.insert(message)).start();
    }

    private void deleteMessageFromDatabase(Message message) {
        new Thread(() -> messageDAO.delete(message)).start();
    }

    private void displayMessage(Message message) {
        Intent intent = new Intent("NewMessageReceived");
        intent.putExtra("message", message.getText());
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}

