package com.SE1730.Group3.JobLink.src.domain.repositories;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.request.ChangePassReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.LoginReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.RegisterReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.response.LoginRespDTO;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import io.reactivex.rxjava3.core.Observable;

public interface IUserRepository {
    Observable<ApiResp<String>> registerUser(RegisterReqDTO request) throws IOException;
    Observable<ApiResp<LoginRespDTO>> loginUser(LoginReqDTO request) throws IOException;
    Observable<ApiResp<String>> changePassUser(ChangePassReqDTO request) throws IOException;
}
