package com.SE1730.Group3.JobLink.src.data.apis;

import com.SE1730.Group3.JobLink.src.data.models.all.NotificationDTO;
import com.SE1730.Group3.JobLink.src.data.models.all.UserDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;

import java.util.List;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IUserApi {
    @GET("user/me")
    Observable<ApiResp<UserDTO>> getCurrentUser();

    @GET("user/notification")
    Observable<ApiResp<List<NotificationDTO>>> getUserNotification();


}
