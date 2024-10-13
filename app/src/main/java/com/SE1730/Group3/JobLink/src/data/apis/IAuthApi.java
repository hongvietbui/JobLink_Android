package com.SE1730.Group3.JobLink.src.data.apis;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.register.RegisterReqDTO;

import retrofit2.Call;
import retrofit2.http.Body;

public interface IAuthApi {
    Call<ApiResp<String>> registerUser(@Body RegisterReqDTO registerRequest);
}
