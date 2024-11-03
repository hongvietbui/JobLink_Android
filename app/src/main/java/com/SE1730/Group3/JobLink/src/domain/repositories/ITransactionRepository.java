package com.SE1730.Group3.JobLink.src.domain.repositories;

import com.SE1730.Group3.JobLink.src.data.models.all.TopUpDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.request.TopupReqDTO;

import java.io.IOException;
import java.sql.Date;
import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface ITransactionRepository {
    Single<ApiResp<String>> getQRCodeByUserId(UUID userId);
    Observable<ApiResp<TopUpDTO>> getTopUpHistory(Date fromDate, Date toDate) throws IOException;

}
