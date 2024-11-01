package com.SE1730.Group3.JobLink.src.domain.repositories;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;

import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;

public interface ITransactionRepository {
    Single<ApiResp<String>> getQRCodeByUserId(UUID userId);
}
