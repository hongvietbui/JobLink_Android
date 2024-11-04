package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.SE1730.Group3.JobLink.R;

public class UserProfileActivity extends BaseBottomActivity{
    private Button btnManageJob, btnLogout, btnManageTransaction;
    Intent intent;
    private void bindingView(){
        btnManageTransaction = findViewById(R.id.btnManageTransaction);
        btnLogout = findViewById(R.id.btnLogout);
        btnManageJob = findViewById(R.id.btnManageJob);
    }

    private void bindingAction(){
        btnManageJob.setOnClickListener(this::onBtnManageJobClick);
        btnLogout.setOnClickListener(this::onBtnLogoutClick);
        btnManageTransaction.setOnClickListener(this::onBtnManageTransactionClick);
    }

    private void onBtnManageTransactionClick(View view) {
    }

    private void onBtnLogoutClick(View view) {
    }

    private void onBtnManageJobClick(View view) {
        intent = new Intent(this, JobManagementNavigationActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        bindingView();
        bindingAction();
    }
}
