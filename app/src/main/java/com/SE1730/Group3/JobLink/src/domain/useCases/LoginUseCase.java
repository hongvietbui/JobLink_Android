package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.login.LoginReqDTO;
import com.SE1730.Group3.JobLink.src.domain.repositories.IAuthRepository;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class LoginUseCase {
    private final IAuthRepository authRepository;

    @Inject
    public LoginUseCase(IAuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public CompletableFuture<ApiResp<String>> execute(String username, String password) throws IOException {
        LoginReqDTO request = LoginReqDTO.builder().
                username(username).
                password(password).
                build();
        return authRepository.loginUser(request);
    }
}
