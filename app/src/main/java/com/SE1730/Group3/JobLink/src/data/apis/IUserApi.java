package com.SE1730.Group3.JobLink.src.data.apis;

import com.SE1730.Group3.JobLink.src.data.models.all.UserDTO;
import com.SE1730.Group3.JobLink.src.data.models.all.UserHompageDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;

import java.util.List;
import java.util.UUID;

import io.reactivex.rxjava3.core.Notification;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface IUserApi {
    @GET("user/me")
    Observable<ApiResp<UserDTO>> getCurrentUser();

//    @GET("user/notifications")
//    Observable<ApiResp<List<Notification>>> getUserNotification();

    @GET("user/worker/{workerId}")
    Observable<ApiResp<UserDTO>> getUserByWorkerId(@Path("workerId") UUID workerId);

    @GET("user/homepage")
    Observable<ApiResp<UserHompageDTO>> GetUserHomepageData();
}
