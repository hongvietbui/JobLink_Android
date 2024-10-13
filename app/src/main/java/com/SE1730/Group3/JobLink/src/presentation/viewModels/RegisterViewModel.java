package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.useCases.RegisterUseCase;

import java.io.IOException;
import java.time.LocalDate;

import jakarta.inject.Inject;

public class RegisterViewModel extends ViewModel {
    private final RegisterUseCase registerUseCase;
    public MutableLiveData<ApiResp<String>> registerResult = new MutableLiveData<>();

    @Inject
    public RegisterViewModel(RegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
    }

    public void RegisterUser(String username, String email, String password, String firstName, String lastName, String phoneNumber, String address, LocalDate dateOfBirth) {
        try{
            ApiResp<String> response = registerUseCase.execute(username, password, email, firstName, lastName, phoneNumber, address, dateOfBirth);
        }catch (IOException ioException){
            ioException.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
