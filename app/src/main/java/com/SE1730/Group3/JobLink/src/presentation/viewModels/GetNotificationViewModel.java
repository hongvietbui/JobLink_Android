package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.data.models.all.NotificationDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.useCases.GetNotificationUseCase;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class GetNotificationViewModel extends ViewModel {
    private final GetNotificationUseCase getNotificationUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    public static MutableLiveData<ApiResp<List<NotificationDTO>>> getNotificationResult = new MutableLiveData<>();

    @Inject
    public GetNotificationViewModel(GetNotificationUseCase getNotificationUseCase) {
        this.getNotificationUseCase = getNotificationUseCase;
    }

    public void getNotification() throws IOException {
        Disposable disposable = getNotificationUseCase.execute()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp ->{
                    getNotificationResult.postValue(resp);
                },error ->{
                    getNotificationResult.postValue(new ApiResp<>(error.getMessage(),null));
                });
        disposables.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
