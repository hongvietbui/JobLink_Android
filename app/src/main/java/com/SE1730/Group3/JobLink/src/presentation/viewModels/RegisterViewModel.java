package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.useCases.RegisterUseCase;

import org.threeten.bp.LocalDate;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import dagger.hilt.android.lifecycle.HiltViewModel;
import javax.inject.Inject;

@HiltViewModel
public class RegisterViewModel extends ViewModel {
    private final RegisterUseCase registerUseCase;
    public MutableLiveData<ApiResp<String>> registerResult = new MutableLiveData<>();

    @Inject
    public RegisterViewModel(RegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
    }

    public void RegisterUser(String username, String email, String password, String firstName, String lastName, String phoneNumber, String address, LocalDate dateOfBirth) throws IOException {
        CompletableFuture<ApiResp<String>> response = registerUseCase.execute(username, password, email, firstName, lastName, phoneNumber, address, dateOfBirth);

        response.thenAccept(result -> {
            registerResult.postValue(result);
        }).exceptionally(e -> {
            registerResult.postValue(new ApiResp<String>(e.getMessage(), null));
            return null;
        });
    }
}
