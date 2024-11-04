package com.SE1730.Group3.JobLink.src.presentation.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

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

    // UI Elements
    private RecyclerView recyclerView;
    private OwnerViewJobAdapter jobAdapter;
    private Spinner spinnerSortBy;
    private Spinner spinnerSortOrder;
    private EditText editTextFilter;
    private Button buttonApplyFilter;
    private ProgressBar progressBar;
    private Button buttonViewApplicants;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_owner_manage_job, container, false);

        // Initialize ViewModel
        jobCreatedByUserViewModel = new ViewModelProvider(this).get(JobCreatedByUserViewModel.class);

        // Bind UI Elements
        recyclerView = view.findViewById(R.id.recycler_view_owner_jobs1);
        spinnerSortBy = view.findViewById(R.id.spinnerSortBy1);
        spinnerSortOrder = view.findViewById(R.id.spinnerSortOrder1);
        buttonApplyFilter = view.findViewById(R.id.buttonApplyFilter1);
        progressBar = view.findViewById(R.id.progressBar1);
        buttonViewApplicants = view.findViewById(R.id.button_view_applicants);

        // Set up RecyclerView with Adapter
        jobAdapter = new OwnerViewJobAdapter(getContext(), job -> {
            Intent intent = new Intent(getContext(), JobDetailsActivity.class);
            intent.putExtra("jobId", job.getId().toString());
            startActivity(intent);
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(jobAdapter);

        // Observe data changes in ViewModel
        jobCreatedByUserViewModel.jobsCreatedResult.observe(getViewLifecycleOwner(), resp -> {
            if (resp != null && resp.getData() != null) {
                jobAdapter.setJobs(resp.getData().getItems());
                progressBar.setVisibility(View.GONE);
            }
        });

        buttonApplyFilter.setOnClickListener(v -> applyFilter());

        fetchJobsCreated();

        return view;
    }

    private void fetchJobsCreated() {
        progressBar.setVisibility(View.VISIBLE);
        try {
            jobCreatedByUserViewModel.getJobsCreatedByUser(1, 10, "", true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void applyFilter() {
        String sortBy = spinnerSortBy.getSelectedItem().toString();
        String sortOrder = spinnerSortOrder.getSelectedItem().toString();

        progressBar.setVisibility(View.VISIBLE);

        try {
            jobCreatedByUserViewModel.getJobsCreatedByUser(1, 10, sortBy, sortOrder.equals("Descending"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
