package com.SE1730.Group3.JobLink.src.data.apis;

import com.SE1730.Group3.JobLink.src.data.models.all.TopUpDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;

import java.sql.Date;
import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ITransactionApi {
    @GET("transaction/vietQR/{userId}")
    Single<ApiResp<String>> getQRCodeByUserId(@Path("userId") UUID userId);

    @GET("transaction/topupHistory")
    Observable<ApiResp<TopUpDTO>> getTransactionByUser(
            @Query("fromDate") Date fromDate,
            @Query("toDate") Date toDate);

}
