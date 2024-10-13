package com.SE1730.Group3.JobLink.src.data.repositoryImpls;

import com.SE1730.Group3.JobLink.src.data.apis.IAuthApi;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.register.RegisterReqDTO;
import com.SE1730.Group3.JobLink.src.domain.repositories.IAuthRepository;

import java.io.IOException;

import jakarta.inject.Inject;


public class AuthRepositoryImpl implements IAuthRepository {
    private final IAuthApi authApi;

    @Inject
    public AuthRepositoryImpl(IAuthApi authApi) {
        this.authApi = authApi;
    }

    public ApiResp<String> registerUser(RegisterReqDTO request) throws IOException {
        return authApi.registerUser(request).execute().body();
    }
}
