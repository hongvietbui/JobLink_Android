package com.SE1730.Group3.JobLink.src.domain.useCases;

import android.content.SharedPreferences;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.dao.IUserDAO;
import com.SE1730.Group3.JobLink.src.domain.entities.User;
import com.SE1730.Group3.JobLink.src.domain.repositories.ITransactionRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetQRCodeByUserIdUseCase {
    private final IUserDAO userDAO;
    private final ITransactionRepository transactionRepository;

    @Inject
    public GetQRCodeByUserIdUseCase(IUserDAO userDAO, ITransactionRepository transactionRepository) {
        this.userDAO = userDAO;
        this.transactionRepository = transactionRepository;
    }

    public Single<ApiResp<String>> execute() {
        return userDAO.getCurrentUser()
                .subscribeOn(Schedulers.io())
                .flatMap(user -> {
                    return transactionRepository.getQRCodeByUserId(user.getId());
                })
                .observeOn(AndroidSchedulers.mainThread());  // Quan sát kết quả trên main thread để cập nhật UI
    }
}
