package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.repositories.IUserRepository;

import java.io.IOException;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class GetWorkerIdByUserIdUseCase {
    private final IUserRepository userRepository;

    @Inject
    public GetWorkerIdByUserIdUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Observable<ApiResp<String>> execute(UUID userId) throws IOException {
        return userRepository.getWorkerIdByUserId(userId);
    }
}
