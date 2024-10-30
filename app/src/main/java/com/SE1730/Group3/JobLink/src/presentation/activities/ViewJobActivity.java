package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.data.models.all.JobDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.api.Pagination;
import com.SE1730.Group3.JobLink.src.domain.useCases.GetJobUseCase;
import com.SE1730.Group3.JobLink.src.presentation.adapters.ViewJobAdapter;

public class ViewJobActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewjob);

        bindingViews();
        setupFilterButton();

        fetchJobsFromApi();
    }

    private void bindingViews() {
        recyclerViewJobs = findViewById(R.id.recyclerViewJobs);
        recyclerViewJobs.setLayoutManager(new LinearLayoutManager(this));

        spinnerSortBy = findViewById(R.id.spinnerSortBy);
        spinnerSortOrder = findViewById(R.id.spinnerSortOrder);  // Initialize the sorting order spinner
        editTextFilter = findViewById(R.id.editTextFilter);
        buttonApplyFilter = findViewById(R.id.buttonApplyFilter);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupFilterButton() {
        buttonApplyFilter.setOnClickListener(v -> {
            String keyword = editTextFilter.getText().toString().trim();
            String sortBy = spinnerSortBy.getSelectedItem().toString();
            boolean isDescending = spinnerSortOrder.getSelectedItem().toString().equals("Descending");  // Get sorting order
            pageIndex = 1;
            fetchJobsFromApi(keyword, sortBy, isDescending);  // Pass the sorting order to the API call
        });
    }

    private void fetchJobsFromApi() {
        fetchJobsFromApi("", "", false);  // Default sorting order is false
    }

    private void fetchJobsFromApi(String filter, String sortBy, boolean isDescending) {
        progressBar.setVisibility(View.VISIBLE);
        try {
            CompletableFuture<ApiResp<Pagination<JobDTO>>> future = getJobUseCase.execute(pageIndex, pageSize, sortBy, isDescending, filter);

            future.thenAccept(apiResp -> {
                progressBar.setVisibility(View.GONE);
                Log.d("ViewJobActivity", "API Response: " + apiResp);

                if (apiResp.getStatus() == 200) {
                    List<JobDTO> jobList = apiResp.getData().getItems();

                    if (jobList != null) {
                        runOnUiThread(() -> {
                            if (pageIndex == 1) {
                                viewJobAdapter = new ViewJobAdapter(jobList);
                                recyclerViewJobs.setAdapter(viewJobAdapter);
                            } else {
                                viewJobAdapter.addJobs(jobList);
                            }
                        });
                    } else {
                        runOnUiThread(() -> showToast("Error parsing job data"));
                    }
                } else {
                    runOnUiThread(() -> showToast("Failed to fetch jobs: " + apiResp.getMessage()));
                }
            }).exceptionally(throwable -> {
                progressBar.setVisibility(View.GONE);
                Log.e("ViewJobActivity", "Error fetching jobs: ", throwable);
                runOnUiThread(() -> showToast("Failed to fetch jobs"));
                return null;
            });

        } catch (Exception e) {
            progressBar.setVisibility(View.GONE);
            Log.e("ViewJobActivity", "Exception occurred while fetching jobs", e);
            showToast("Error fetching jobs");
        }
    }

    private void showToast(String message) {
        Toast.makeText(ViewJobActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}
