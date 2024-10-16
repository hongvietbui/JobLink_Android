package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.request.LoginReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.response.LoginRespDTO;
import com.SE1730.Group3.JobLink.src.domain.repositories.IUserRepository;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

public class LoginUseCase {
    private final IUserRepository userRepository;

    @Inject
    public LoginUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CompletableFuture<ApiResp<LoginRespDTO>> execute(String username, String password) throws IOException {
        LoginReqDTO request = LoginReqDTO.builder()
                .username(username)
                .password(password)
                .build();

        return userRepository.loginUser(request);
    }
}
