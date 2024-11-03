package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.domain.dao.INotificationDAO;
import com.SE1730.Group3.JobLink.src.domain.entities.Notification;
import com.SE1730.Group3.JobLink.src.presentation.adapters.NotificationAdapter;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.GetNotificationViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotificationActivity extends AppCompatActivity {
    private RecyclerView recyclerViewNotifications;
    private NotificationAdapter notificationAdapter;
    private GetNotificationViewModel getNotificationViewModel;
    private List<Notification> notificationsList = new ArrayList<>();

    @Inject
    INotificationDAO notificationDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        // Initialize ViewModel
        getNotificationViewModel = new ViewModelProvider(this).get(GetNotificationViewModel.class);

        // Binding view
        bindingView();

        // RecyclerView
        setupRecyclerView();

        // Observer notification
        observeNotifications();
    }

    private void bindingView() {
        recyclerViewNotifications = findViewById(R.id.recyclerViewNotification);
    }

    private void setupRecyclerView() {
        notificationAdapter = new NotificationAdapter(notificationsList);
        recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewNotifications.setAdapter(notificationAdapter);
    }

    private void observeNotifications(){
        getNotificationViewModel.getNotificationList().observe(this, new Observer<List<Notification>>() {
            @Override
            public void onChanged(List<Notification> notifications) {
                if (notifications != null && !notifications.isEmpty()) {
                    notificationsList.clear();
                    notificationsList.addAll(notifications);
                    notificationAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(NotificationActivity.this, "An error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
