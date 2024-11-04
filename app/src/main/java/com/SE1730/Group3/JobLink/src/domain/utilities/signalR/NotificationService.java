package com.SE1730.Group3.JobLink.src.domain.utilities.signalR;

import static android.Manifest.permission.POST_NOTIFICATIONS;
import static androidx.core.app.ActivityCompat.requestPermissions;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.SE1730.Group3.JobLink.src.domain.dao.INotificationDAO;
import com.SE1730.Group3.JobLink.src.domain.entities.Notification;
import com.SE1730.Group3.JobLink.src.presentation.activities.NotificationActivity;
import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;
import com.microsoft.signalr.HubConnectionState;
import com.squareup.moshi.Moshi;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@AndroidEntryPoint
public class NotificationService extends Service {
    private HubConnection hubConnection;
    private Moshi moshi;
    private INotificationDAO notificationDAO;

    private CompositeDisposable disposables = new CompositeDisposable();

    private String userId = "0";

    private Context context;

    @Inject
    public NotificationService(Moshi moshi, INotificationDAO notificationDAO, Context context) {
        this.hubConnection = HubConnectionBuilder.create("http://10.0.2.2:8080/hub/notification?userId=" + userId).build();
        this.moshi = moshi;
        this.notificationDAO = notificationDAO;
        this.context = context;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        startHubConnection();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startHubConnection(){
        if (hubConnection != null && hubConnection.getConnectionState() == HubConnectionState.DISCONNECTED) {
            var notificationDisposable = hubConnection.start()
                    .doOnComplete(() -> Log.d("ChatHubService", "Connection started with userId: " + userId))
                    .doOnError(error -> Log.e("ChatHubService", "Error starting connection", error))
                    .subscribe();

            disposables.add(disposables);

            hubConnection.on("ReceiveNotification", (title, message, timestamp) -> {
                Notification notification = Notification.builder()
                        .title(title)
                        .message(message)
                        .timestamp(timestamp)
                        .build();
                notificationDAO.insert(notification);

                displayNotification(notification);

                Intent broadcastIntent = new Intent("com.SE1730.Group3.JobLink.Notification");
                broadcastIntent.putExtra("notificationTitle", title);
                broadcastIntent.putExtra("notificationMessage", message);
                sendBroadcast(broadcastIntent);
            }, String.class, String.class, String.class);
        }
    }

    @Override
    public void onDestroy(){
        if(hubConnection!=null && hubConnection.getConnectionState() == HubConnectionState.CONNECTED){
            hubConnection.stop()
                    .doOnComplete(() -> Log.d("ChatHubService", "Connection stopped"))
                    .doOnError(error -> Log.e("ChatHubService", "Error stopping connection", error))
                    .subscribe();
        }

        disposables.clear();
        super.onDestroy();
    }

    public void updateUserIdAndReconnect(String userId){
        this.userId = userId;
        if(hubConnection != null){
            hubConnection.stop();
            this.hubConnection = HubConnectionBuilder.create("http://10.0.2.2:8080/hub/notification?userId=" + userId).build();
            startHubConnection();
        }
    }

    private void displayNotification(Notification notification){
        try{
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            String channelId = "JobLink_Notification_Channel";

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                NotificationChannel channel = new NotificationChannel(channelId, "JobLink Notification Channel", NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }

            PendingIntent pendingIntent;

            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.S){
                Intent intent = new Intent(context, NotificationActivity.class);
                int flags = PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE; // Sử dụng FLAG_IMMUTABLE cho API 31 trở lên
                pendingIntent = PendingIntent.getActivity(context, 0, intent, flags);
            }else{
                Intent intent = new Intent(context, NotificationActivity.class);
                pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                    .setContentTitle(notification.getTitle())
                    .setContentText(notification.getMessage())
                    .setSmallIcon(android.R.drawable.ic_dialog_info)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            notificationManager.notify((int)System.currentTimeMillis(), builder.build());
        }catch(Exception e){
            Log.e("NotificationService", "Error displaying notification", e);
        }
    }
}
