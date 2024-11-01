package com.SE1730.Group3.JobLink.src.data.repositoryImpls;

import com.SE1730.Group3.JobLink.src.data.apis.ITransactionApi;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.repositories.ITransactionRepository;

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
}
