package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ViewJobActivity extends AppCompatActivity {

    private RecyclerView recyclerViewJobs;
    private ViewJobAdapter viewJobAdapter;
    private Spinner spinnerSortBy;
    private Spinner spinnerSortOrder;
    private EditText editTextFilter;
    private Button buttonApplyFilter;
    private View progressBar;

    private int pageIndex = 1;
    private final int pageSize = 10;
    private boolean isLoading = false;
    private boolean hasMorePages = true;  // Tracks if more pages are available

    @Inject
    GetJobUseCase getJobUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewjob);

        bindingViews();
        setupFilterButton();
        setupRecyclerView();

        try {
            fetchJobsFromApi();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void bindingViews() {
        recyclerViewJobs = findViewById(R.id.recyclerViewJobs);
        spinnerSortBy = findViewById(R.id.spinnerSortBy);
        spinnerSortOrder = findViewById(R.id.spinnerSortOrder);
        editTextFilter = findViewById(R.id.editTextFilter);
        buttonApplyFilter = findViewById(R.id.buttonApplyFilter);
        progressBar = findViewById(R.id.progressBar);
    }

    private void setupFilterButton() {
        buttonApplyFilter.setOnClickListener(v -> {
            String keyword = editTextFilter.getText().toString().trim();
            String sortBy = spinnerSortBy.getSelectedItem().toString();
            boolean isDescending = spinnerSortOrder.getSelectedItem().toString().equals("Descending");
            pageIndex = 1;
            hasMorePages = true;
            try {
                fetchJobsFromApi(keyword, sortBy, isDescending);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void setupRecyclerView() {
        recyclerViewJobs.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewJobs.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (layoutManager != null && !isLoading && hasMorePages &&
                        layoutManager.findLastVisibleItemPosition() >= viewJobAdapter.getItemCount() - 3) {
                    pageIndex++;
                    try {
                        fetchJobsFromApi();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void fetchJobsFromApi() throws IOException {
        fetchJobsFromApi("", "", false);
    }

    private void fetchJobsFromApi(String filter, String sortBy, boolean isDescending) throws IOException {
        if (!hasMorePages) return;
        isLoading = true;
        progressBar.setVisibility(View.VISIBLE);

        CompletableFuture<ApiResp<Pagination<JobDTO>>> future =
                getJobUseCase.execute(pageIndex, pageSize, sortBy, isDescending, filter);

        future.thenAccept(apiResp -> {
            isLoading = false;
            progressBar.setVisibility(View.GONE);
            if (apiResp.getStatus() == 200) {
                List<JobDTO> jobList = apiResp.getData().getItems();
                hasMorePages = !jobList.isEmpty();

                runOnUiThread(() -> {
                    if (pageIndex == 1) {
                        viewJobAdapter = new ViewJobAdapter(jobList);
                        recyclerViewJobs.setAdapter(viewJobAdapter);
                    } else {
                        viewJobAdapter.addJobs(jobList);
                    }
                });
            } else {
                runOnUiThread(() -> showToast("Failed to fetch jobs: " + apiResp.getMessage()));
            }
        }).exceptionally(throwable -> {
            isLoading = false;
            progressBar.setVisibility(View.GONE);
            Log.e("ViewJobActivity", "Error fetching jobs: ", throwable);
            runOnUiThread(() -> showToast("Failed to fetch jobs"));
            return null;
        });
    }

    private void showToast(String message) {
        Toast.makeText(ViewJobActivity.this, message, Toast.LENGTH_SHORT).show();
    }
}