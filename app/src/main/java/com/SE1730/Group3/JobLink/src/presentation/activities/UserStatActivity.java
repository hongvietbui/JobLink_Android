package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.data.models.all.UserHompageDTO;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.GetUserHomepageDataViewModel;

import java.io.IOException;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class UserStatActivity extends BaseBottomActivity {

    private ImageView imageAvatar;
    private TextView tvUsername;
    private TextView tvBalance;
    private Button btnDeposit;
    private Button btnWithdraw;
    private TextView tvEarningToday, tvEarningThisMonth,
            tvTotalDeposit, tvTaskCreated;


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
        setContent(R.layout.activity_user_stat);
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
    }

    private void loadUserData() throws IOException {
        viewModel.GetUserHomepageData();

        viewModel.getUserHomepageDataResult.observe(this, result -> {
            if (result != null && result.getData() != null) {
                UserHompageDTO userData = result.getData();
                // Cập nhật UI ở đây
                tvUsername.setText(userData.getUserName());
                tvBalance.setText("Balance: " + userData.getAccountBalance() + " VND");
                tvEarningToday.setText(userData.getAmountEarnedToday() + " VND");
                tvEarningThisMonth.setText(userData.getAmountEarnedThisMonth() + " VND");
                tvTotalDeposit.setText(userData.getDepositAmount() + " VND");
                tvTaskCreated.setText(userData.getCreateJobThisMonth()+"");

            } else {
                Toast.makeText(this, "Failed to load user homepage data", Toast.LENGTH_SHORT).show();
            }
        });

    }
}