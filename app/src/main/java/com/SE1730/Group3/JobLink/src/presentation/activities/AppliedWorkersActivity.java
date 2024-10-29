package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.domain.entities.User;
import com.SE1730.Group3.JobLink.src.presentation.adapters.AppliedWorkerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AppliedWorkersActivity extends AppCompatActivity implements AppliedWorkerAdapter.OnWorkerClickListener {
    private RecyclerView recyclerView;
    private AppliedWorkerAdapter adapter;
    private List<User> appliedWorkers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applied_workers);

        // Khởi tạo RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Giả lập dữ liệu công nhân (thay thế bằng dữ liệu thực từ API hoặc database)
        appliedWorkers = new ArrayList<>();
        loadAppliedWorkers();

        // Khởi tạo Adapter và gán nó cho RecyclerView
        adapter = new AppliedWorkerAdapter(appliedWorkers, this);
        recyclerView.setAdapter(adapter);
    }

    private void loadAppliedWorkers() {

    }

    @Override
    public void onWorkerClick(User worker) {
        Intent intent = new Intent(this, ChatActivity.class);
        intent.putExtra("workerId", worker.getId());
        startActivity(intent);
    }
}
