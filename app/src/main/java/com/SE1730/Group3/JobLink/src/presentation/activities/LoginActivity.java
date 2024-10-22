package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.LoginViewModel;
import com.google.android.material.snackbar.Snackbar;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private Button btnLogin;
    private EditText edtUsername, edtPassword;
    private TextView tvRegister, tvForgotPassword;

    private void bindingViews() {
        btnLogin = findViewById(R.id.btnLogin);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        tvRegister = findViewById(R.id.tvRegister);
        tvForgotPassword = findViewById(R.id.tvForgetPassword);
    }

    private void bindingActions() {
        btnLogin.setOnClickListener(this::onBtnLoginClick);
    }

    private void onBtnLoginClick(View view) {
        try {
            loginViewModel.loginUser(edtUsername.getText().toString(), edtPassword.getText().toString());

            HandleLoginResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void HandleLoginResult() {
        loginViewModel.loginResult.observe(this, apiResp -> {
            if(apiResp != null) {
                Snackbar.make(findViewById(android.R.id.content), apiResp.getMessage(), Snackbar.LENGTH_SHORT).show();
            }else {
                Snackbar.make(findViewById(android.R.id.content), "Login Failed", Snackbar.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        bindingViews();
        bindingActions();
    }


}