package com.SE1730.Group3.JobLink.src.data.apis;

import com.SE1730.Group3.JobLink.src.data.models.all.NotificationDTO;
import com.SE1730.Group3.JobLink.src.data.models.all.TopUpDTO;
import com.SE1730.Group3.JobLink.src.data.models.all.TransactionDTO;
import com.SE1730.Group3.JobLink.src.data.models.all.UserDTO;
import com.SE1730.Group3.JobLink.src.data.models.all.UserHompageDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiReq;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.request.TopupReqDTO;

import java.util.List;
import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface IUserApi {
    @GET("user/me")
    Observable<ApiResp<UserDTO>> getCurrentUser();

    @GET("user/notification")
    Observable<ApiResp<List<NotificationDTO>>> getUserNotification();

    @GET("user/topup")
    Observable<ApiResp<List<TopUpDTO>>> getUserTransaction(ApiReq<TopupReqDTO> request);

    @GET("user/worker/{workerId}")
    Observable<ApiResp<UserDTO>> getUserByWorkerId(@Query("workerId") UUID workerId);

    @GET("user/homepage")
    Observable<ApiResp<UserHompageDTO>> GetUserHomepageData();
}
