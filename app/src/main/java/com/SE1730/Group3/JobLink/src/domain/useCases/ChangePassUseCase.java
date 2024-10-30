package com.SE1730.Group3.JobLink.src.domain.useCases;

import io.reactivex.rxjava3.core.Observable;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.request.ChangePassReqDTO;
import com.SE1730.Group3.JobLink.src.domain.repositories.IUserRepository;

import java.io.IOException;
import java.util.UUID;

import javax.inject.Inject;

public class ChangePassUseCase {
    private final IUserRepository userRepository;

    @Inject
    public ChangePassUseCase(IUserRepository userRepository){
        this.userRepository = userRepository;
    }

    public Observable<ApiResp<String>> execute(UUID userId,
                                               String currentPassword,
                                               String newPassword) throws IOException
    {
        ChangePassReqDTO request = ChangePassReqDTO.builder()
                .userId(userId)
                .currentPassword(currentPassword)
                .newPassword(newPassword)
                .build();

        return userRepository.changePassUser(request);
    }
}
