package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.useCases.SendOtpUseCase;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
@HiltViewModel
public class SentOtpViewModel extends ViewModel {
    private final SendOtpUseCase sentOtpUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public static MutableLiveData<ApiResp<String>> otpconfirmResult = new MutableLiveData<>();
    @Inject
    public SentOtpViewModel(SendOtpUseCase sentOtpUseCase) {
        this.sentOtpUseCase = sentOtpUseCase;
    }
    public void sendOtp(String email)  throws IOException {
        Disposable disposable= sentOtpUseCase.execute(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp ->{
                    otpconfirmResult.postValue(resp);
                },error ->{
                    otpconfirmResult.postValue(new ApiResp<String>(error.getMessage(),null));
                });
        disposables.add(disposable);
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

}
