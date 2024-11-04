package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.data.models.all.UserHompageDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.GetUserHomepageDataViewModel;

import java.io.IOException;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeActivity extends BaseBottomActivity {
    private ImageView imageAvatar;
    private TextView tvUsername;
    private TextView tvBalance;
    private Button btnDeposit;
    private Button btnWithdraw;
    private TextView tvEarningToday, tvEarningThisMonth,
            tvTotalDeposit, tvTaskCreated;
    private RecyclerView rvJobList;

    private GetUserHomepageDataViewModel viewModel;

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
        Intent intent = new Intent(this, WithdrawActivity.class);
        startActivity(intent);
    }

    private void onBtnDepositClick(View view) {
        Intent intent = new Intent(this, TransferActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContent(R.layout.activity_home);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        viewModel = new ViewModelProvider(this).get(GetUserHomepageDataViewModel.class);
        bindingView();
        try {
            loadUserData();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        bindingAction();
        setSelectedNavigationItem(R.id.navigation_home);

    }

    private void loadUserData() throws IOException {
        viewModel.GetUserHomepageData();

        viewModel.getUserHomepageDataResult.observe(this, result -> {
            if (result != null && result.getData() != null) {
                UserHompageDTO userData = result.getData();
                // Cập nhật UI ở đây
                tvUsername.setText(userData.getUserName());
                tvBalance.setText(userData.getAccountBalance());
                tvEarningToday.setText(userData.getAmountEarnedToday());
                tvEarningThisMonth.setText(userData.getAmountEarnedThisMonth());
                tvTotalDeposit.setText(userData.getDepositAmount());
                tvTaskCreated.setText(userData.getCreateJobThisMonth()+"");

            } else {
                Toast.makeText(this, "Failed to load user homepage data", Toast.LENGTH_SHORT).show();
            }
        });

    }
}