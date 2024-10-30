package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.useCases.VerifyOtpUseCase;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel

public class VerifyOtpViewModel extends ViewModel {
    private final VerifyOtpUseCase verifyOtpUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public static MutableLiveData<ApiResp<String>> otpconfirmverifyResult = new MutableLiveData<>();
    @Inject
    public VerifyOtpViewModel(VerifyOtpUseCase verifyOtpUseCase) {
        this.verifyOtpUseCase = verifyOtpUseCase;
    }
    public void VerifyOtp(String email, String Code)  throws IOException {
        Disposable disposable= verifyOtpUseCase.execute(email,Code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp ->{
                    otpconfirmverifyResult.postValue(resp);
                },error ->{
                    otpconfirmverifyResult.postValue(new ApiResp<String>(error.getMessage(),null));
                });
        disposables.add(disposable);
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }



}
