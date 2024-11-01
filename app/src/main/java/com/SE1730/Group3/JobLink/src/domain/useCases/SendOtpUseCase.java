package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.request.ForgetPassReqDTO;
import com.SE1730.Group3.JobLink.src.domain.repositories.IUserRepository;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class SendOtpUseCase {
    private final IUserRepository userRepository;
    @Inject
    public SendOtpUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Observable<ApiResp<String>> execute(String Email) throws IOException {
        ForgetPassReqDTO request = ForgetPassReqDTO.builder()
                .Email(Email)
                .build();
        return userRepository.sendOTP(request);
    }
}
