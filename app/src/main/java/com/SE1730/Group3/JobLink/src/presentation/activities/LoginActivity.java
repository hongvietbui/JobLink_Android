package com.SE1730.Group3.JobLink.src.presentation.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.SE1730.Group3.JobLink.R;
import com.SE1730.Group3.JobLink.src.presentation.viewModels.LoginViewModel;
import com.google.android.material.snackbar.Snackbar;
import com.SE1730.Group3.JobLink.src.domain.dao.IUserDAO;
import com.SE1730.Group3.JobLink.src.domain.useCases.LoginUseCase;
import com.SE1730.Group3.JobLink.src.domain.utilities.signalR.TransferHubService;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private Button btnLogin;
    private EditText edtUsername, edtPassword;
    private TextView tvRegister, tvForgotPassword;

    private Disposable loginObservable;

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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);
        bindingViews();
        bindingActions();
    }


}