package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;

public class HomeActivity extends AppCompatActivity {
    private ImageView imageAvatar;
    private TextView tvUsername;
    private TextView tvBalance;
    private Button btnDeposit;
    private Button btnWithdraw;
    private TextView tvEarningToday, tvEarningThisMonth,
            tvTotalDeposit, tvTaskCreated;
    private RecyclerView rvJobList;

    private void bindingView(){
        imageAvatar = findViewById(R.id.imageAvatar);
        tvUsername = findViewById(R.id.tvUsername);
        tvBalance = findViewById(R.id.tvBalance);
        btnDeposit = findViewById(R.id.btnDeposit);
        btnWithdraw = findViewById(R.id.btnWithdraw);
        tvEarningToday = findViewById(R.id.tvEarningToday);
        tvEarningThisMonth = findViewById(R.id.tvEarningThisMonth);
        tvTotalDeposit = findViewById(R.id.tvTotalDeposit);
        tvTaskCreated = findViewById(R.id.tvTaskCreated);
        rvJobList = findViewById(R.id.rvJobList);
    }

    private void bindingAction(){
        btnDeposit.setOnClickListener(this::onBtnDepositClick);
        btnWithdraw.setOnClickListener(this::onBtnWithdrawClick);
        
    }

    private void onBtnWithdrawClick(View view) {
    }

    private void onBtnDepositClick(View view) {
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        bindingView();
        bindingAction();
    }
}