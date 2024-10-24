package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.useCases.ResetPassUseCase;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ResetForgotPassViewModel extends ViewModel {
    private final ResetPassUseCase resetPassUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public static MutableLiveData<ApiResp<String>> ResetPassResult = new MutableLiveData<>();

    @Inject
    public ResetForgotPassViewModel(ResetPassUseCase resetPassUseCase) {
        this.resetPassUseCase = resetPassUseCase;
    }

    public  void ResetPass(String email, String Password)  throws IOException {
        Disposable disposable= resetPassUseCase.execute(email,Password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp ->{
                    ResetPassResult.postValue(resp);
                },error ->{
                    ResetPassResult.postValue(new ApiResp<String>(error.getMessage(),null));
                });
        disposables.add(disposable);
    }
    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }

}
