package com.SE1730.Group3.JobLink.src.presentation.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.data.models.all.NotificationDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.presentation.adapters.NotificationAdapter;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.GetNotificationViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class NotificationFragment extends Fragment {

    private RecyclerView recyclerViewNotifications;
    private NotificationAdapter notificationAdapter;
    private GetNotificationViewModel getNotificationViewModel;
    private List<NotificationDTO> notificationsList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getNotificationViewModel = new ViewModelProvider(this).get(GetNotificationViewModel.class);

        bindView(view);
        setupRecyclerView();
        observeNotifications();

        try {
            getNotificationViewModel.getNotification();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void bindView(View view) {
        recyclerViewNotifications = view.findViewById(R.id.recyclerViewNotification);
    }

    private void setupRecyclerView() {
        notificationAdapter = new NotificationAdapter(notificationsList);
        recyclerViewNotifications.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewNotifications.setAdapter(notificationAdapter);
    }

    private void observeNotifications() {
        getNotificationViewModel.getNotificationResult.observe(getViewLifecycleOwner(), new Observer<ApiResp<List<NotificationDTO>>>() {
            @Override
            public void onChanged(ApiResp<List<NotificationDTO>> apiResp) {
                if (apiResp != null && apiResp.getStatus() == 200 && apiResp.getData() != null) {
                    notificationsList.clear();
                    notificationsList.addAll(apiResp.getData());
                    notificationAdapter.notifyDataSetChanged();
                    Toast.makeText(getContext(), "Notifications loaded successfully!", Toast.LENGTH_SHORT).show();
                } else if (apiResp != null) {
                    Toast.makeText(getContext(), "Failed to load notifications. Please try again.", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("NotificationFragment", "ApiResp is null.");
                    Toast.makeText(getContext(), "An unexpected error occurred. Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
