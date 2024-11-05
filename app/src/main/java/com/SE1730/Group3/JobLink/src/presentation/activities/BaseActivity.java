package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.domain.utilities.signalR.NotificationService;
import com.SE1730.Group3.JobLink.src.domain.utilities.signalR.TransferHubService;
import com.SE1730.Group3.JobLink.src.domain.utilities.signalR.broadcastReceivers.NotificationReceiver;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

public class BaseActivity extends AppCompatActivity {

    private final Map<Integer, Class<?>> menuActivityMap = new HashMap<>();
    private NotificationReceiver notificationReceiver = new NotificationReceiver();

    @Inject
    TransferHubService transferHubService;

    @Inject
    NotificationService notificationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bindingMenu();

        IntentFilter filter = new IntentFilter("com.SE1730.Group3.JobLink.NEW_NOTIFICATION");
        Intent intent = registerReceiver(notificationReceiver, filter);
    }

    @Override
    public Intent registerReceiver(@Nullable BroadcastReceiver receiver, IntentFilter filter) {
        if (Build.VERSION.SDK_INT >= 34 && getApplicationInfo().targetSdkVersion >= 34) {
            return super.registerReceiver(receiver, filter, Context.RECEIVER_NOT_EXPORTED);
        } else {
            return super.registerReceiver(receiver, filter);
        }
    }

    private void bindingMenu() {
        menuActivityMap.put(R.id.nav_home, HomeActivity.class);
        menuActivityMap.put(R.id.nav_manage_job, JobManagementNavigationActivity.class);
        menuActivityMap.put(R.id.nav_manage_transaction, TopUpHistoryActivity.class);
        menuActivityMap.put(R.id.nav_user_stat, UserStatActivity.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Class<?> activityClass = menuActivityMap.get(item.getItemId());

        if (activityClass != null) {
            Intent intent = new Intent(this, activityClass);
            startActivity(intent);
            return true;
        }

        if (item.getItemId() == R.id.nav_logout) {
            logout();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(notificationReceiver);
    }

    // Phương thức đăng xuất
    private void logout() {
        Intent intent = new Intent(this, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
