package com.SE1730.Group3.JobLink.src.data.apis;

import com.SE1730.Group3.JobLink.src.data.models.all.UserDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;

import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface IUserApi {
    @GET("user/me")
    Observable<ApiResp<UserDTO>> getCurrentUser();

    @GET("user/notifications")
    Observable<ApiResp<UUID>> getUserNotification();
}
