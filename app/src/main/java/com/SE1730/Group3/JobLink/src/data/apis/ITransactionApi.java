package com.SE1730.Group3.JobLink.src.data.apis;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;

import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ITransactionApi {
    @GET("transaction/vietQR/{userId}")
    Single<ApiResp<String>> getQRCodeByUserId(@Path("userId") UUID userId);
}
