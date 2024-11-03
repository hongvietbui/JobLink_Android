package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.useCases.ChangePassUseCase;

import java.io.IOException;
import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.schedulers.Schedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

@HiltViewModel
public class ChangePassViewModel extends ViewModel {
    private final ChangePassUseCase changePassUseCase;

    private final CompositeDisposable disposables = new CompositeDisposable();

    public final MutableLiveData<ApiResp<String>> changePassResult = new MutableLiveData<>();

    @Inject
    public ChangePassViewModel(ChangePassUseCase changePassUseCase) {
        this.changePassUseCase = changePassUseCase;
    }

    public void ChangePassUser(UUID userId, String currentPass, String newPass)
            throws IOException {
        Disposable disposable = changePassUseCase.execute(userId, currentPass, newPass)
                .subscribeOn(Schedulers.io())
                .subscribe(resp -> {
                    changePassResult.postValue(resp);
                }, error -> {
                    changePassResult.postValue(new ApiResp<String>(error.getMessage(), null));
                });
        disposables.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
