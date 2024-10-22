package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.request.ResetPassDTO;
import com.SE1730.Group3.JobLink.src.domain.repositories.IUserRepository;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class ResetPassUseCase {
     private final IUserRepository userRepository;
@Inject
    public ResetPassUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Observable<ApiResp<String>> execute(String Email, String Password) throws IOException {
        ResetPassDTO request = ResetPassDTO.builder()
                .Email(Email)
                .Password(Password)
                .build();
        return userRepository.ResetPassword(request);
    }
}
