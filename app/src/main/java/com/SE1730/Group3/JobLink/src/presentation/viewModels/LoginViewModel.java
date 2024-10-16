package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.useCases.LoginUseCase;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public class LoginViewModel extends ViewModel {
    private final LoginUseCase loginUseCase;
    public MutableLiveData<ApiResp<String>> loginResult = new MutableLiveData<>();

    public LoginViewModel(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    public void loginUser(String username, String password) throws IOException {

        CompletableFuture<ApiResp<String>> response = loginUseCase.execute(username, password);

        response.thenAccept(result -> {
            loginResult.postValue(result);
        }).exceptionally(e -> {
            loginResult.postValue(new ApiResp<String>(e.getMessage(), null));
            return null;
        });
    }
}
