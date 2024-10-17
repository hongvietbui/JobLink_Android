package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.useCases.RegisterUseCase;

import org.threeten.bp.LocalDate;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

import javax.inject.Inject;

@HiltViewModel
public class RegisterViewModel extends ViewModel {
    private final RegisterUseCase registerUseCase;

    private final CompositeDisposable disposables = new CompositeDisposable();

    public MutableLiveData<ApiResp<String>> registerResult = new MutableLiveData<>();


    @Inject
    public RegisterViewModel(RegisterUseCase registerUseCase) {
        this.registerUseCase = registerUseCase;
    }

    public void RegisterUser(String username, String email, String password, String firstName, String lastName, String phoneNumber, String address, LocalDate dateOfBirth) throws IOException {
        Disposable disposable = registerUseCase.execute(username, password, email, firstName, lastName, phoneNumber, address, dateOfBirth)
                .subscribeOn(Schedulers.io())
                .subscribe(resp -> {
                    registerResult.postValue(resp);
                }, error -> {
                    registerResult.postValue(new ApiResp<String>(error.getMessage(), null));
                });

        disposables.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
