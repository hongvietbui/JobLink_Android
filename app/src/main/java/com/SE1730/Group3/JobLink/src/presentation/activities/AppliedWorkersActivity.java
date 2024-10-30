package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.data.models.all.JobWorkerDTO;
import com.SE1730.Group3.JobLink.src.domain.entities.User;
import com.SE1730.Group3.JobLink.src.presentation.adapters.AppliedWorkerAdapter;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.ViewAppliedWorkerViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AppliedWorkersActivity extends BaseActivity implements AppliedWorkerAdapter.OnWorkerClickListener {
    private RecyclerView recyclerView;
    private AppliedWorkerAdapter adapter;
    private List<JobWorkerDTO> appliedWorkers;
    private ViewAppliedWorkerViewModel viewAppliedWorkerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_workers);

        viewAppliedWorkerViewModel = new ViewModelProvider(this).get(ViewAppliedWorkerViewModel.class);

        // Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        appliedWorkers = new ArrayList<>();
        loadAppliedWorkers();

        // Khởi tạo Adapter và gán nó cho RecyclerView
        adapter = new AppliedWorkerAdapter(appliedWorkers, this);
        recyclerView.setAdapter(adapter);
    }


    private void loadAppliedWorkers() {

        try {
            Intent intent = new Intent();
            String jobId = intent.getStringExtra("jobId");
            UUID jobIdParse = UUID.fromString(jobId);

            String accessToken= getAccessTokenFromSharedPreferences();

            viewAppliedWorkerViewModel.ViewAppliedWorker(jobIdParse, accessToken);
        }catch (IOException e){
            throw new RuntimeException(e);
        }

        viewAppliedWorkerViewModel.viewAppliedWorkerResult.observe(this, result -> {
            if(result!=null){

                List<JobWorkerDTO> list = result.getData();

                for (JobWorkerDTO dto: list) {
                    appliedWorkers.add(dto);
                }
                Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(this, "Failed to load applied worker", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getAccessTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String accessToken = sharedPreferences.getString("accessToken", null);

        if (accessToken != null) {
            return accessToken;
        } else {
            return null;
        }
    }

    @Override
    public void onWorkerClick(User worker) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("workerId", worker.getId());
        startActivity(intent);
    }
}
