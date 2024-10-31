package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.data.models.all.JobWorkerDTO;
import com.SE1730.Group3.JobLink.src.data.models.all.UserHompageDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.useCases.GetUserHomepageDataUseCase;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class GetUserHomepageDataViewModel extends ViewModel {
    private final GetUserHomepageDataUseCase getUserHomepageDataUseCase;

    private final CompositeDisposable disposables = new CompositeDisposable();

    public final MutableLiveData<ApiResp<UserHompageDTO>> getUserHomepageDataResult = new MutableLiveData<>();

    @Inject
    public GetUserHomepageDataViewModel(GetUserHomepageDataUseCase getUserHomepageDataUseCase){
        this.getUserHomepageDataUseCase = getUserHomepageDataUseCase;
    }

    public void GetUserHomepageData()
            throws IOException
    {
        Disposable disposable = getUserHomepageDataUseCase.execute()
                .subscribeOn(Schedulers.io())
                .subscribe(resp -> {
                    getUserHomepageDataResult.postValue(resp);
                }, error -> {
                    getUserHomepageDataResult.postValue(new ApiResp<UserHompageDTO>(error.getMessage(), null));
                });
        disposables.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
