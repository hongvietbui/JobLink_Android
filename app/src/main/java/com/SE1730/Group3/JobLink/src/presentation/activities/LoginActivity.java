package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.content.Intent;
import android.os.Bundle;
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
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@AndroidEntryPoint
public class LoginActivity extends BaseActivity {
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
        // Call login api
        loginObservable = loginUseCase.execute(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> {
            if(result) {
                Toast.makeText(this, "Login successfully", Toast.LENGTH_SHORT).show();
                intent = new Intent(this, TopUpHistoryActivity.class);
                startActivity(intent);
                userDAO.getCurrentUser()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(resp -> {
                transferHubService.updateUserIdAndReconnect(resp.getId().toString());
                }, error -> {
                    error.printStackTrace();
                });
            } else {
                Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(loginObservable != null && !loginObservable.isDisposed())
            loginObservable.dispose();
    }
}

