package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.domain.dao.INotificationDAO;
import com.SE1730.Group3.JobLink.src.domain.entities.Notification;
import com.SE1730.Group3.JobLink.src.presentation.adapters.NotificationAdapter;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.GetNotificationViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotificationActivity extends BaseBottomActivity {
    private RecyclerView recyclerViewNotifications;
    private NotificationAdapter notificationAdapter;
    private GetNotificationViewModel getNotificationViewModel;
    private List<Notification> notificationsList = new ArrayList<>();

    @Inject
    INotificationDAO notificationDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_notification);

        getNotificationViewModel = new ViewModelProvider(this).get(GetNotificationViewModel.class);

        bindingView();

        setupRecyclerView();

        observeNotifications();
        setSelectedNavigationItem(R.id.navigation_notification);

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
