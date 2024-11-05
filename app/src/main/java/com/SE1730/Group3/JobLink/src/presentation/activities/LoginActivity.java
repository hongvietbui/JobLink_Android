package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.domain.dao.IUserDAO;
import com.SE1730.Group3.JobLink.src.domain.useCases.LoginUseCase;
import com.SE1730.Group3.JobLink.src.domain.utilities.signalR.ChatHubService;
import com.SE1730.Group3.JobLink.src.domain.utilities.signalR.NotificationService;
import com.SE1730.Group3.JobLink.src.domain.utilities.signalR.TransferHubService;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private Button btnLogin;
    private TextView tvRegister, tvForgotPass;
    private ImageView ivEye;
    private ProgressBar progressBar;
    private Disposable loginObservable;
    private Boolean isPwdVisible = false;
    Intent intent;

    CompositeDisposable disposables = new CompositeDisposable();

    @Inject
    LoginUseCase loginUseCase;

    @Inject
    TransferHubService transferHubService;

    @Inject
    NotificationService notificationService;

    @Inject
    ChatHubService chatHubService;

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
        progressBar = findViewById(R.id.loginProgressBar);
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
        if (isPwdVisible) {
            edtPassword.setTransformationMethod(new android.text.method.PasswordTransformationMethod());
            ivEye.setImageResource(R.drawable.ic_eye); // Update with appropriate icon resource for 'closed eye'
        } else {
            edtPassword.setTransformationMethod(null);
            ivEye.setImageResource(R.drawable.ic_eye_off); // Update with appropriate icon resource for 'open eye'
        }
        isPwdVisible = !isPwdVisible;
        edtPassword.setSelection(edtPassword.getText().length());
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

        //make loading spinner visible
        btnLogin.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        // Call login api
        Disposable loginDisposable = loginUseCase.execute(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
//                    // Debug kết quả từ API
//                    Log.d("LoginDebug", "Login result: " + result);
                    // make loading spinner invisible
                    progressBar.setVisibility(View.GONE);
                    if (result) {
                        Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
                        intent = new Intent(this, UserProfileActivity.class);
                        startActivity(intent);

                        disposables.add(userDAO.getCurrentUser()
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(resp -> {
                                    Log.d("LoginDebug", "User ID: " + resp.getId().toString());
                                    transferHubService.updateUserIdAndReconnect(resp.getId().toString());
                                    notificationService.updateUserIdAndReconnect(resp.getId().toString());
                                    chatHubService.updateUserIdAndReconnect(resp.getId().toString());
                                }, error -> {
                                    error.printStackTrace();
                                }));
                        finish();
                    } else {
                        Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
                        btnLogin.setEnabled(true);
                    }
                }, error -> {
                    // Log lỗi nếu API call gặp vấn đề
                    Log.e("LoginDebug", "Login error", error);
                    progressBar.setVisibility(View.GONE);
                    btnLogin.setEnabled(true);
                });

        disposables.add(loginDisposable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        disposables.clear();
    }
}

