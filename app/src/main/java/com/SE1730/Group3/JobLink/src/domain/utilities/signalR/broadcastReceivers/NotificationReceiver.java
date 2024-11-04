package com.SE1730.Group3.JobLink.src.domain.utilities.signalR.broadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String title = intent.getStringExtra("title");
        String message = intent.getStringExtra("message");
        String timestamp = intent.getStringExtra("timestamp");

        Log.d("NotificationReceiver", "Received notification: " + title + " - " + message + " - " + timestamp);
    }
}
