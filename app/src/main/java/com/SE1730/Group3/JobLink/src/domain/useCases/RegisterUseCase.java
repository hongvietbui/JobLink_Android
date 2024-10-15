package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.register.RegisterReqDTO;
import com.SE1730.Group3.JobLink.src.domain.repositories.IUserRepository;

import org.threeten.bp.LocalDate;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import jakarta.inject.Inject;

public class RegisterUseCase {
    private final IUserRepository userRepository;

    @Inject
    public RegisterUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public CompletableFuture<ApiResp<String>> execute(String username, String password, String email, String firstName, String lastName, String phoneNumber, String address, LocalDate dateOfBirth) throws IOException {

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

        return userRepository.registerUser(request);
    }
}
