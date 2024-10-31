package com.SE1730.Group3.JobLink.src.presentation.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.presentation.activities.JobDetailsActivity;
import com.SE1730.Group3.JobLink.src.presentation.adapters.OwnerViewJobAdapter;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.JobCreatedByUserViewModel;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.JobDetailViewModel;

import java.io.IOException;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class OwnerManageJobFragment extends Fragment {
    private JobDetailViewModel jobDetailViewModel;
    private JobCreatedByUserViewModel jobCreatedByUserViewModel;
    private RecyclerView recyclerView;
    private OwnerViewJobAdapter jobAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_owner_manage_job, container, false);

        jobCreatedByUserViewModel = new ViewModelProvider(this).get(JobCreatedByUserViewModel.class);

        jobAdapter = new OwnerViewJobAdapter(getContext(), job -> {
            Intent intent = new Intent(getContext(), JobDetailsActivity.class);
            intent.putExtra("jobId", job.getId().toString());
            startActivity(intent);
        });

        recyclerView = view.findViewById(R.id.recycler_view_owner_jobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(jobAdapter);

        jobCreatedByUserViewModel.jobsCreatedResult.observe(getViewLifecycleOwner(), resp -> {
            if (resp != null && resp.getData() != null) {
                jobAdapter.setJobs(resp.getData().getItems());
            }
        });

        fetchJobsCreated();

        return view;
    }

    private void fetchJobsCreated() {
        try {
            jobCreatedByUserViewModel.getJobsCreatedByUser(1, 10, "", false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
