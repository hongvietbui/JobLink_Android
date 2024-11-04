package com.SE1730.Group3.JobLink.src.presentation.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.presentation.adapters.WorkerViewJobAdapter;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.JobApplierByUserViewModel;

import java.io.IOException;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

@AndroidEntryPoint
public class WorkerManageJobFragment extends Fragment {
    private static final String TAG = "WorkerManageJobFragment";
    private JobApplierByUserViewModel jobApplierByUserViewModel;
    private WorkerViewJobAdapter jobAdapter;
    private RecyclerView recyclerView;

    private Spinner spinnerSortBy;
    private Spinner spinnerSortOrder;
    private Button applyFilterButton;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_worker_manage_job, container, false);

        // Initialize ViewModel and RecyclerView
        jobApplierByUserViewModel = new ViewModelProvider(this).get(JobApplierByUserViewModel.class);
        jobAdapter = new WorkerViewJobAdapter(getContext());
        recyclerView = view.findViewById(R.id.recycler_view_Worker_jobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(jobAdapter);

        // Initialize Spinner and Button
        spinnerSortBy = view.findViewById(R.id.spinnerSortBy2);
        spinnerSortOrder = view.findViewById(R.id.spinnerSortOrder2);
        applyFilterButton = view.findViewById(R.id.buttonApplyFilter1);

        // Set up button click listener
        applyFilterButton.setOnClickListener(v -> fetchJobsApplied());

        // Observe ViewModel LiveData
        jobApplierByUserViewModel.jobsAppliedResult.observe(getViewLifecycleOwner(), resp -> {
            if (resp != null && resp.getData() != null) {
                Log.d(TAG, "Jobs found: " + resp.getData().getItems().size());
                jobAdapter.setJobs(resp.getData().getItems());
            } else {
                Log.e(TAG, resp == null ? "Response is null." : "No jobs found in response.");
            }
        });

        fetchJobsApplied(); // Initial data fetch

        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void fetchJobsApplied() {
        String sortBy = spinnerSortBy.getSelectedItem().toString();
        boolean isDescending = "Descending".equals(spinnerSortOrder.getSelectedItem().toString());

        try {
            jobApplierByUserViewModel.getJobsAppliedByUser(1, 10, sortBy, isDescending);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
