package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.domain.dao.IUserDAO;
import com.SE1730.Group3.JobLink.src.domain.useCases.LoginUseCase;
import com.SE1730.Group3.JobLink.src.domain.utilities.signalR.TransferHubService;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private TextView tvRegister, tvForgotPass;
    private ImageView ivEye;
    private Disposable loginObservable;
    Intent intent;

    @Inject
    LoginUseCase loginUseCase;

    @Inject
    TransferHubService transferHubService;

    @Inject
    IUserDAO userDAO;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindingViews();
        setEvents();
    }

    private void bindingViews(){
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        tvRegister = findViewById(R.id.tvRegister);
        tvForgotPass = findViewById(R.id.tvForgetPassword);
        btnLogin = findViewById(R.id.btnLogin);
        ivEye = findViewById(R.id.ivEye);
    }

    private void setEvents(){
        btnLogin.setOnClickListener(v -> {
            try {
                login();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        tvForgotPass.setOnClickListener(this::onTvForgotPassClick);
        tvRegister.setOnClickListener(this::onTvRegisterClick);
        ivEye.setOnClickListener(this::onIvEyeClick);
    }

    private void onIvEyeClick(View view) {
        //hide and show password

    }

    private void onTvRegisterClick(View view) {
        intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    private void onTvForgotPassClick(View view) {
        intent = new Intent(this, ResetPasswordActivity.class);
        startActivity(intent);
    }

    private void login() throws IOException {
        String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        // Debug username và password
        Log.d("LoginDebug", "Username: " + username);
        Log.d("LoginDebug", "Password: " + password);

        // Call login api
        loginObservable = loginUseCase.execute(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
                    // Debug kết quả từ API
                    Log.d("LoginDebug", "Login result: " + result);

                    if (result) {
                        Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
                        intent = new Intent(this, ViewJobsActivity.class);
                        startActivity(intent);

                        userDAO.getCurrentUser()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(resp -> {
                                    Log.d("LoginDebug", "User ID: " + resp.getId().toString());
                                    transferHubService.updateUserIdAndReconnect(resp.getId().toString());
                                }, error -> {
                                    error.printStackTrace();
                                });
                    } else {
                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                    }
                }, error -> {
                    // Log lỗi nếu API call gặp vấn đề
                    Log.e("LoginDebug", "Login error", error);
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(loginObservable != null && !loginObservable.isDisposed())
            loginObservable.dispose();
    }
}

