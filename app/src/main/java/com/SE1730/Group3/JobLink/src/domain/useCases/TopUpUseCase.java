package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.apis.ITransactionApi;
import com.SE1730.Group3.JobLink.src.data.models.all.TopUpDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.repositories.ITransactionRepository;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class TopUpUseCase {
    private final ITransactionRepository transactionRepository;

    @Inject
    public TopUpUseCase(ITransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Observable<ApiResp<List<TopUpDTO>>> execute(Date fromDate, Date toDate) throws IOException {
        return transactionRepository.getTopUpHistory(fromDate, toDate);
    }
}
