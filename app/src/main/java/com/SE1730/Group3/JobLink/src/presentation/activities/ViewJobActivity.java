package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.listjob.ListJobReqDTO;
import com.SE1730.Group3.JobLink.src.domain.useCases.GetJobUseCase;
import com.SE1730.Group3.JobLink.src.presentation.adapters.LocalDateJsonAdapter;
import com.SE1730.Group3.JobLink.src.presentation.adapters.ViewJobAdapter;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import dagger.hilt.android.AndroidEntryPoint;
import jakarta.inject.Inject;
@AndroidEntryPoint
public class ViewJobActivity extends AppCompatActivity {

    private RecyclerView recyclerViewJobs;
    private ViewJobAdapter viewJobAdapter;

    @Inject
    GetJobUseCase getJobUseCase;  // Use-case to fetch jobs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewjob);

        bindingViews();

        fetchJobsFromApi();
    }

    private void bindingViews() {
        recyclerViewJobs = findViewById(R.id.recyclerViewJobs);
        recyclerViewJobs.setLayoutManager(new LinearLayoutManager(this));
    }

    private void fetchJobsFromApi() {
        try {
            CompletableFuture<ApiResp<String>> future = getJobUseCase.execute(1, 10, "name", false, "");

            future.thenAccept(apiResp -> {
                // Log the entire API response for debugging
                Log.d("ViewJobActivity", "API Response: " + apiResp);

                if (apiResp.getStatus() == 200) {
                    List<ListJobReqDTO> jobList = parseJobListFromApiResponse(apiResp.getData());

                    if (jobList != null) {
                        runOnUiThread(() -> {
                            viewJobAdapter = new ViewJobAdapter(jobList);
                            recyclerViewJobs.setAdapter(viewJobAdapter);
                        });
                    } else {
                        runOnUiThread(() ->
                                Toast.makeText(ViewJobActivity.this, "Error parsing job data", Toast.LENGTH_SHORT).show()
                        );
                    }
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(ViewJobActivity.this, "Failed to fetch jobs: " + apiResp.getMessage(), Toast.LENGTH_SHORT).show()
                    );
                }
            }).exceptionally(throwable -> {
                Log.e("ViewJobActivity", "Error fetching jobs: ", throwable);
                runOnUiThread(() ->
                        Toast.makeText(ViewJobActivity.this, "Failed to fetch jobs", Toast.LENGTH_LONG).show()
                );
                return null;
            });

        } catch (IOException e) {
            Log.e("ViewJobActivity", "IOException occurred while fetching jobs", e);
        }
    }

    private List<ListJobReqDTO> parseJobListFromApiResponse(String apiResponseData) {
        Log.d("ViewJobActivity", "Raw API Response Data: " + apiResponseData);  // Log the raw data

        Moshi moshi = new Moshi.Builder()
                .add(new LocalDateJsonAdapter())  // Registering the LocalDate adapter
                .build();

        Type type = Types.newParameterizedType(List.class, ListJobReqDTO.class);
        JsonAdapter<List<ListJobReqDTO>> adapter = moshi.adapter(type);

        try {
            return adapter.fromJson(apiResponseData);
        } catch (IOException e) {
            Log.e("ViewJobActivity", "Error parsing job list from JSON", e);
            return null;
        }
    }

}
