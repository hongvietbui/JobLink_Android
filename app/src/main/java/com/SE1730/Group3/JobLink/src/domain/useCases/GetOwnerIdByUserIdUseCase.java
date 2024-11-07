package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.repositories.IJobRepository;
import com.SE1730.Group3.JobLink.src.domain.repositories.IUserRepository;

import java.io.IOException;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class GetOwnerIdByUserIdUseCase {
    private final IUserRepository userRepository;

    @Inject
    public GetOwnerIdByUserIdUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Observable<ApiResp<String>> execute(UUID userId) throws IOException {
        return userRepository.getOwnerIdByUserId(userId);
    }
}
