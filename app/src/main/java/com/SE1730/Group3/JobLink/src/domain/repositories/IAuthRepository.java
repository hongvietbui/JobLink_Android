package com.SE1730.Group3.JobLink.src.domain.repositories;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.register.RegisterReqDTO;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface IAuthRepository {
    CompletableFuture<ApiResp<String>> registerUser(RegisterReqDTO request) throws IOException;
}
