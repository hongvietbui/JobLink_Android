package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.register.RegisterReqDTO;
import com.SE1730.Group3.JobLink.src.domain.repositories.IAuthRepository;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.inject.Inject;
import lombok.Builder;

@Builder
public class RegisterUseCase {
    private final IAuthRepository authRepository;

    @Inject
    public RegisterUseCase(IAuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public ApiResp<String> execute(String username, String password, String email, String firstName, String lastName, String phoneNumber, String address, LocalDate dateOfBirth) throws IOException {
        RegisterReqDTO request = RegisterReqDTO.builder()
                .username(username)
                .password(password)
                .email(email)
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .address(address)
                .dateOfBirth(dateOfBirth)
                .build();
        return authRepository.registerUser(request);
    }
}
