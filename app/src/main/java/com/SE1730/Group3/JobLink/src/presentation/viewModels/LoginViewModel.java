package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.response.LoginRespDTO;
import com.SE1730.Group3.JobLink.src.domain.useCases.LoginUseCase;
import com.SE1730.Group3.JobLink.src.domain.useCases.RegisterUseCase;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class LoginViewModel extends ViewModel {
    private final LoginUseCase loginUseCase;

    private final CompositeDisposable disposables = new CompositeDisposable();
    public MutableLiveData<ApiResp<Boolean>> loginResult = new MutableLiveData<>();

    @Inject
    public LoginViewModel(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    public void loginUser(String username, String password) throws IOException {
        Disposable disposable = loginUseCase.execute(username, password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
                    //check if the resp status is true or not
                    if(resp){
                        loginResult.postValue(new ApiResp<Boolean>("Login sucessfully", true));
                    }else
                        loginResult.postValue(new ApiResp<Boolean>("Login failed", false));
                }, error -> {
                    loginResult.postValue(new ApiResp<Boolean>(error.getMessage(), false));
                });

        disposables.add(disposable);
    }

    public void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
