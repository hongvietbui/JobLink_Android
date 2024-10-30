    package com.SE1730.Group3.JobLink.src.presentation.fragments;

    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.Button;

    import androidx.annotation.NonNull;
    import androidx.fragment.app.Fragment;
    import androidx.lifecycle.Observer;
    import androidx.lifecycle.ViewModelProvider;
    import androidx.recyclerview.widget.LinearLayoutManager;
    import androidx.recyclerview.widget.RecyclerView;

    import com.SE1730.Group3.JobLink.R;
    import com.SE1730.Group3.JobLink.src.data.models.all.JobDTO;
    import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
    import com.SE1730.Group3.JobLink.src.data.models.api.Pagination;
    import com.SE1730.Group3.JobLink.src.presentation.adapters.OwnerViewJobAdapter;
    import com.SE1730.Group3.JobLink.src.presentation.viewModels.JobCreatedByUserViewModel;

    import java.io.IOException;
    import java.util.UUID;

    import dagger.hilt.android.AndroidEntryPoint;

    @AndroidEntryPoint
    public class OwnerManageJobFragment extends Fragment {

        private JobCreatedByUserViewModel jobCreatedByUserViewModel;

        private RecyclerView recyclerView;
        private OwnerViewJobAdapter jobAdapter; // Create an adapter for your jobs
        private Button viewApplicantsButton;

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_owner_manage_job, container, false);

            jobCreatedByUserViewModel = new ViewModelProvider(this).get(JobCreatedByUserViewModel.class);

            recyclerView = view.findViewById(R.id.recycler_view_owner_jobs);
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            jobAdapter = new OwnerViewJobAdapter(new OwnerViewJobAdapter.OnItemClickListener() {
                @Override
                public void onViewDetailsClick(JobDTO job) {
                    viewApplicants(job.getId());
                }
            });
            recyclerView.setAdapter(jobAdapter);

            // Observe the jobs applied LiveData
            jobCreatedByUserViewModel.jobsCreatedResult.observe(getViewLifecycleOwner(), new Observer<ApiResp<Pagination<JobDTO>>>() {
                @Override
                public void onChanged(ApiResp<Pagination<JobDTO>> resp) {
                    if (resp != null && resp.getData() != null) {
                        jobAdapter.setJobs(resp.getData().getItems());
                    }
                }
            });

            // Fetch jobs
            fetchJobsCreated();

            return view;
        }

        private void fetchJobsCreated() {
            // Set the required parameters for pagination
            try {
                jobCreatedByUserViewModel.getJobsCreatedByUser(1, 10, "", false);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private void viewApplicants(UUID jobId) {
            // Navigate to the ViewApplicantsFragment or handle the logic here
            // For example, you can create a new fragment and pass the jobId to it
        }
    }
