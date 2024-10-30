package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.all.UserDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.repositories.IUserRepository;

import java.io.IOException;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class GetUserByWorkerIdUseCase {
    private final IUserRepository userRepository;

    @Inject
    public GetUserByWorkerIdUseCase(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Observable<ApiResp<UserDTO>> execute(UUID workerId) throws IOException {
        return userRepository.getUserByWorkerId(workerId);
    }
}
