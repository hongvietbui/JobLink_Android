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
import com.SE1730.Group3.JobLink.src.data.models.all.UserDTO;
import com.SE1730.Group3.JobLink.src.domain.useCases.GetUserByWorkerIdUseCase;
import com.SE1730.Group3.JobLink.src.presentation.adapters.AppliedWorkerAdapter;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.ViewAppliedWorkerViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class AppliedWorkersActivity extends BaseActivity implements AppliedWorkerAdapter.OnWorkerClickListener {
    private RecyclerView recyclerView;
    private AppliedWorkerAdapter adapter;
    private List<JobWorkerDTO> jobWorkerDTOList;
    private List<UserDTO> appliedWorkers = new ArrayList<>(); // Khởi tạo danh sách
    private ViewAppliedWorkerViewModel viewAppliedWorkerViewModel;

    @Inject
    GetUserByWorkerIdUseCase getUserByWorkerIdUseCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_workers);

        viewAppliedWorkerViewModel = new ViewModelProvider(this).get(ViewAppliedWorkerViewModel.class);

        // Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        try {
            loadAppliedWorkers(); // Gọi phương thức tải dữ liệu
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadAppliedWorkers() throws IOException {
        Intent intent = getIntent(); // Lấy intent từ activity
        String jobId = intent.getStringExtra("jobId");
        UUID jobIdParse = UUID.fromString(jobId);
        String accessToken = getAccessTokenFromSharedPreferences();

        viewAppliedWorkerViewModel.ViewAppliedWorker(jobIdParse, accessToken);
        viewAppliedWorkerViewModel.viewAppliedWorkerResult.observe(this, result -> {
            if (result != null && result.getData() != null) {
                jobWorkerDTOList = result.getData();
                for (JobWorkerDTO jobWorkerDTO : jobWorkerDTOList) {
                    try {
                        getUserByWorkerIdUseCase.execute(jobWorkerDTO.getWorkerId())
                                .subscribeOn(Schedulers.io())
                                .subscribe(resp -> {
                                    if (resp.getData() != null) {
                                        appliedWorkers.add(resp.getData());
                                        runOnUiThread(() -> adapter.notifyDataSetChanged()); // Cập nhật adapter trên UI thread
                                    }
                                }, error -> {
                                    // Xử lý lỗi
                                    runOnUiThread(() -> Toast.makeText(this, "Fetch failed", Toast.LENGTH_SHORT).show());
                                });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                Toast.makeText(this, result.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed to load applied workers", Toast.LENGTH_SHORT).show();
            }
        });

        // Khởi tạo Adapter và gán nó cho RecyclerView
        adapter = new AppliedWorkerAdapter(appliedWorkers, this, jobId, accessToken);
        recyclerView.setAdapter(adapter);
    }

    private String getAccessTokenFromSharedPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        return sharedPreferences.getString("accessToken", null);
    }

    @Override
    public void onWorkerClick(UserDTO worker) {
        Intent chatIntent = new Intent(this, ChatActivity.class);
        // Thêm dữ liệu cần thiết vào intent
        startActivity(chatIntent);
    }
}

