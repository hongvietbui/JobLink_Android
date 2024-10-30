package  com.SE1730.Group3.JobLink.src.presentation.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.presentation.adapters.WorkerViewJobAdapter;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.JobApplierByUserViewModel;

import java.io.IOException;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WorkerManageJobFragment extends Fragment {
    private JobApplierByUserViewModel jobApplierByUserViewModel;
    private WorkerViewJobAdapter jobAdapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_worker_manage_job, container, false);

        jobApplierByUserViewModel = new ViewModelProvider(this).get(JobApplierByUserViewModel.class);
        jobAdapter = new WorkerViewJobAdapter(job -> {
            // Handle job click here if needed
        });

        recyclerView = view.findViewById(R.id.recycler_view_Worker_jobs);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(jobAdapter);

        // Observe the jobs applied result
        jobApplierByUserViewModel.jobsAppliedResult.observe(getViewLifecycleOwner(), resp -> {
            if (resp != null && resp.getData() != null) {
                jobAdapter.setJobs(resp.getData().getItems());
            } else {
                // Handle empty response or error state
                Log.e("WorkerManageJobFragment", "No jobs found or error in response");
            }
        });

        // Fetch jobs applied by the user
        fetchJobsApplied();

        return view;
    }

    private void fetchJobsApplied() {
        try {
            jobApplierByUserViewModel.getJobsAppliedByUser(1, 10, "", false);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}