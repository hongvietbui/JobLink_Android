package com.SE1730.Group3.JobLink.src.domain.repositories;

import com.SE1730.Group3.JobLink.src.data.models.all.UserDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.request.ForgetPassReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.LoginReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.OtpReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.RegisterReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.ResetPassDTO;
import com.SE1730.Group3.JobLink.src.data.models.response.LoginRespDTO;

import java.io.IOException;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface IUserRepository {
    Observable<ApiResp<String>> registerUser(RegisterReqDTO request) throws IOException;
    Observable<ApiResp<LoginRespDTO>> loginUser(LoginReqDTO request) throws IOException;
    Observable<ApiResp<Boolean>> logoutUser() throws IOException;
    Observable<ApiResp<UserDTO>> getCurrentUser() throws IOException;
    Observable<ApiResp<String>> sendOTP (ForgetPassReqDTO request) throws IOException;
    Observable<ApiResp<String>> VerifyOtp (OtpReqDTO request) throws IOException;
    Observable<ApiResp<String>> ResetPassword (ResetPassDTO request) throws IOException;
}
