package com.SE1730.Group3.JobLink.src.data.apis;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.request.ChatDTOReq;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IChatApi {
    @POST("chat/send")
    Observable<ApiResp<String>> SendMessage(@Body ChatDTOReq chatDTOReq);
}
