package com.SE1730.Group3.JobLink.src.data.repositoryImpls;

import com.SE1730.Group3.JobLink.src.data.apis.ITransactionApi;
import com.SE1730.Group3.JobLink.src.data.models.all.TopUpDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiReq;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.request.TopupReqDTO;
import com.SE1730.Group3.JobLink.src.domain.repositories.ITransactionRepository;

import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public class TransactionRepositoryImpl implements ITransactionRepository {
    private final ITransactionApi transactionApi;

    @Inject
    public TransactionRepositoryImpl(ITransactionApi transactionApi) {
        this.transactionApi = transactionApi;
    }

    @Override
    public Single<ApiResp<String>> getQRCodeByUserId(UUID userId) {
        return transactionApi.getQRCodeByUserId(userId);
    }

    @Override
    public Observable<ApiResp<List<TopUpDTO>>> getTopUpHistory(Date fromDate, Date toDate) throws IOException {
        return transactionApi.getTransactionByUser(fromDate, toDate);
    }


}
