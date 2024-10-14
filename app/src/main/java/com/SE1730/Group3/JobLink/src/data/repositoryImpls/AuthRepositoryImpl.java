package com.SE1730.Group3.JobLink.src.data.repositoryImpls;

import com.SE1730.Group3.JobLink.src.data.apis.IAuthApi;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.register.RegisterReqDTO;
import com.SE1730.Group3.JobLink.src.domain.repositories.IAuthRepository;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;


public class AuthRepositoryImpl implements IAuthRepository {
    private final IAuthApi authApi;

    @Inject
    public AuthRepositoryImpl(IAuthApi authApi) {
        this.authApi = authApi;
    }

    public CompletableFuture<ApiResp<String>> registerUser(RegisterReqDTO request) throws IOException {
        CompletableFuture<ApiResp<String>> future = new CompletableFuture<>();

        authApi.registerUser(request).enqueue(new retrofit2.Callback<ApiResp<String>>() {
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
