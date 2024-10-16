package com.SE1730.Group3.JobLink.src.domain.repositories;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.request.LoginReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.RegisterReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.response.LoginRespDTO;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

public interface IUserRepository {
    CompletableFuture<ApiResp<String>> registerUser(RegisterReqDTO request) throws IOException;
    CompletableFuture<ApiResp<LoginRespDTO>> loginUser(LoginReqDTO request) throws IOException;
}
