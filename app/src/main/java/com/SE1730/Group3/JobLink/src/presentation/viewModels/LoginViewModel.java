package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.response.LoginRespDTO;
import com.SE1730.Group3.JobLink.src.domain.useCases.LoginUseCase;
import com.SE1730.Group3.JobLink.src.domain.useCases.RegisterUseCase;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    private final LoginUseCase loginUseCase;
    public MutableLiveData<ApiResp<LoginRespDTO>> loginResult = new MutableLiveData<>();

    @Inject
    public LoginViewModel(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    public void loginUser(String username, String password) throws IOException {
        loginUseCase.execute(username, password).thenAccept(resp -> {
            //do something
        }).exceptionally(error -> {
            //do something
            return null;
        });
    }
}
