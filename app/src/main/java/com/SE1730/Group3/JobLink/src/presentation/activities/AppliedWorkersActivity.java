package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.data.models.all.JobWorkerDTO;
import com.SE1730.Group3.JobLink.src.data.models.all.UserDTO;
import com.SE1730.Group3.JobLink.src.domain.useCases.GetUserByWorkerIdUseCase;
import com.SE1730.Group3.JobLink.src.domain.useCases.ViewAppliedWorkerUseCase;
import com.SE1730.Group3.JobLink.src.presentation.adapters.AppliedWorkerAdapter;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.ViewAppliedWorkerViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class AppliedWorkersActivity extends BaseActivity implements AppliedWorkerAdapter.OnWorkerClickListener {
    @Inject
    ViewAppliedWorkerUseCase viewAppliedWorkerUseCase;

    CompositeDisposable disposables = new CompositeDisposable();

    private RecyclerView recyclerView;
    private AppliedWorkerAdapter adapter;
    private List<JobWorkerDTO> jobWorkerDTOList;
    private List<UserDTO> appliedWorkers = new ArrayList<>(); // Khởi tạo danh sách

    @Inject
    GetUserByWorkerIdUseCase getUserByWorkerIdUseCase;

    private ViewAppliedWorkerViewModel viewAppliedWorkerViewModel; // Xóa annotation @Inject

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_workers);

        // Khởi tạo ViewModel bằng ViewModelProvider
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

        Disposable disposable = viewAppliedWorkerUseCase.execute(UUID.fromString(jobId))
                .subscribeOn(Schedulers.io())
                .subscribe(resp -> {
                    jobWorkerDTOList = resp.getData();
                    for (JobWorkerDTO jobWorkerDTO : jobWorkerDTOList) {
                        try {
                            Disposable getUserByWorkerIdDisposable = getUserByWorkerIdUseCase.execute(jobWorkerDTO.getWorkerId())
                                    .subscribeOn(Schedulers.io())
                                    .subscribe(getUserByWorkerResp -> {
                                        if (resp.getData() != null) {
                                            appliedWorkers.add(getUserByWorkerResp.getData());
                                            runOnUiThread(() -> adapter.notifyDataSetChanged()); // Cập nhật adapter trên UI thread
                                        }
                                    }, error -> {
                                        // Xử lý lỗi
                                        runOnUiThread(() -> Toast.makeText(this, "Fetch failed", Toast.LENGTH_SHORT).show());
                                        Log.e("AppliedWorkersActivity", "Failed to fetch user by worker id", error);
                                    });

                            disposables.add(getUserByWorkerIdDisposable);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }, error -> {
                    runOnUiThread(() -> Toast.makeText(this, "Fetch failed", Toast.LENGTH_SHORT).show());
                    Log.e("AppliedWorkersActivity", "Failed to fetch applied workers" + error.getStackTrace(), error);
                });
        disposables.add(disposable);

        // Khởi tạo Adapter và gán nó cho RecyclerView
        adapter = new AppliedWorkerAdapter(appliedWorkers, this, jobIdParse, viewAppliedWorkerViewModel);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onWorkerClick(UserDTO worker) {
        Intent intent = getIntent(); // Lấy intent từ activity
        String jobId = intent.getStringExtra("jobId");

        // Xử lý sự kiện khi worker được click
        Intent intent1 = new Intent(this, ChatActivity.class);
        intent1.putExtra("jobId", jobId);
        intent1.putExtra("receiverId", worker.getId().toString());
        startActivity(intent1);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}
