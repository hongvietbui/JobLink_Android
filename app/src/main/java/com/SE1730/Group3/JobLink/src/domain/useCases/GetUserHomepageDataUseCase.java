package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.all.UserHompageDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.repositories.IUserRepository;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class GetUserHomepageDataUseCase {
    private final IUserRepository userRepository;

    @Inject
    public GetUserHomepageDataUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Observable<ApiResp<UserHompageDTO>> execute() throws IOException {
        return userRepository.getUserHomepageData();
    }
}
