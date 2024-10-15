package com.SE1730.Group3.JobLink.src.data.repositoryImpls;

import com.SE1730.Group3.JobLink.src.data.apis.IAuthApi;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiReq;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.register.RegisterReqDTO;
import com.SE1730.Group3.JobLink.src.domain.dao.IUnitOfWork;
import com.SE1730.Group3.JobLink.src.domain.repositories.IUserRepository;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;


public class UserRepositoryImpl implements IUserRepository {
    private final IAuthApi authApi;
    private final IUnitOfWork unitOfWork;

    @Inject
    public UserRepositoryImpl(IAuthApi authApi, IUnitOfWork unitOfWork) {
        this.authApi = authApi;
        this.unitOfWork = unitOfWork;
    }

    public CompletableFuture<ApiResp<String>> registerUser(RegisterReqDTO request) throws IOException {
        CompletableFuture<ApiResp<String>> future = new CompletableFuture<>();

        ApiReq<RegisterReqDTO> apiReq = new ApiReq<>(request);

        authApi.registerUser(apiReq).enqueue(new retrofit2.Callback<ApiResp<String>>() {
            @Override
            public void onResponse(Call<ApiResp<String>> call, Response<ApiResp<String>> response) {
                if (response.isSuccessful()) {
                    future.complete(response.body());
                } else {
                    future.completeExceptionally(new IOException("Failed to register user"));
                }
            }

            @Override
            public void onFailure(Call<ApiResp<String>> call, Throwable t) {
                future.completeExceptionally(new IOException("Failed to register user"));
            }
        });

        return future;
    }
}
