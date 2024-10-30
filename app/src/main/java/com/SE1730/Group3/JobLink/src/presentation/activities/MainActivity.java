package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.SE1730.Group3.JobLink.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {
    private Button btnRegister, btnJobDetails, btnLogin, btnTransfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindingViews();
        setEvents();
    }

    private void bindingViews(){
        btnRegister = findViewById(R.id.btnRegister);
        btnJobDetails = findViewById(R.id.btnJobDetails);
        btnLogin = findViewById(R.id.btnLogin);
        btnTransfer = findViewById(R.id.btnTransfer);
    }

    private void setEvents(){
        btnRegister.setOnClickListener(v -> openRegisterActivity());
        btnJobDetails.setOnClickListener(v -> openJobDetailsActivity());
        btnLogin.setOnClickListener(v -> openLoginActivity());
        btnTransfer.setOnClickListener(v -> openTransferActivity());
    }

    private void openRegisterActivity(){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void openJobDetailsActivity(){
        Intent intent = new Intent(this, JobDetailsActivity.class);
        startActivity(intent);
    }

    private void openLoginActivity(){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    private void openTransferActivity(){
        Intent intent = new Intent(this, TransferActivity.class);
        startActivity(intent);
    }
}
