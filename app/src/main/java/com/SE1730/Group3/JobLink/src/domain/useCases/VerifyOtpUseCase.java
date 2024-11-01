package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.request.OtpReqDTO;
import com.SE1730.Group3.JobLink.src.domain.repositories.IUserRepository;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class VerifyOtpUseCase {
    private final IUserRepository userRepository;
    @Inject
    public VerifyOtpUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public Observable<ApiResp<String>> execute(String Email, String Code) throws IOException {
        OtpReqDTO request = OtpReqDTO.builder()
                .Email(Email)
                .Code(Code)
                .build();
        return userRepository.VerifyOtp(request);
    }
}
