package com.SE1730.Group3.JobLink.src.data.apis;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiReq;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.request.ForgetPassReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.LoginReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.OtpReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.RegisterReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.ResetPassDTO;
import com.SE1730.Group3.JobLink.src.data.models.response.LoginRespDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IAuthApi {
    @POST("auth/register")
    Call<ApiResp<String>> registerUser(@Body ApiReq<RegisterReqDTO> request);

    @POST("auth/login")
    Call<ApiResp<LoginRespDTO>> loginUser(@Body ApiReq<LoginReqDTO> request);
    @POST("sent-otp")
    Call<ApiResp<String>> sendOTP(@Body ApiReq<ForgetPassReqDTO> request);
    @POST("verify-otp")
    Call<ApiResp<String>> VerifyOtp(@Body ApiReq<OtpReqDTO> request);
    @POST("reset-password")
    Call<ApiResp<String>> ResetPassword(@Body ApiReq<ResetPassDTO> request);
}
