package com.SE1730.Group3.JobLink.src.domain.utilities.signalR;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.SE1730.Group3.JobLink.src.data.apis.IChatApi;
import com.SE1730.Group3.JobLink.src.data.apis.IUserApi;
import com.SE1730.Group3.JobLink.src.data.models.all.TransactionDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.ChatDTOReq;
import com.SE1730.Group3.JobLink.src.domain.dao.IMessageDAO;
import com.SE1730.Group3.JobLink.src.domain.entities.Message;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;
import com.squareup.moshi.Moshi;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import lombok.NoArgsConstructor;

@AndroidEntryPoint
@NoArgsConstructor
public class ChatHubService extends Service {
    private CompositeDisposable disposables = new CompositeDisposable();

    private HubConnection hubConnection;
    private String userId = "0";
    private IMessageDAO messageDAO;
    private Moshi moshi;
    private IChatApi chatApi;

    @Inject
    public ChatHubService(Moshi moshi, IMessageDAO messageDAO, IChatApi chatApi) {
        this.moshi = moshi;
        this.messageDAO = messageDAO;
        this.chatApi = chatApi;
        this.hubConnection = HubConnectionBuilder.create("http://10.0.2.2:8080/hub/chat?userId=" + userId).build();
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
        disposables.clear();
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

    public void sendMessage(Message message) {
        saveMessageToDatabase(message);
        try {
            var chatDTOReq = ChatDTOReq.builder()
                    .jobId(message.getJobId().toString())
                    .message(message.getText())
                    .senderId(message.getSenderId().toString())
                    .receiverId(message.getReceiverId().toString())
                    .isWorker(message.getIsWorker())
                    .build();
            var sendMessageDisposable = chatApi.SendMessage(chatDTOReq)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(resp -> {
                        Log.d("ChatHubService", "Message sent successfully");
                    }, error -> {
                        Log.e("ChatHubService", "Error sending message", error);
                        deleteMessageFromDatabase(message);
                    });

            disposables.add(sendMessageDisposable);
        } catch (Exception e) {
            Log.e("ChatHubService", "Error sending message", e);
            deleteMessageFromDatabase(message);
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

    public void updateUserIdAndReconnect(String userId){
        this.userId = userId;
        if(hubConnection != null){
            hubConnection.stop();
            this.hubConnection = HubConnectionBuilder.create("http://10.0.2.2:8080/hub/notification?userId=" + userId).build();
            startHubConnection();
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}

