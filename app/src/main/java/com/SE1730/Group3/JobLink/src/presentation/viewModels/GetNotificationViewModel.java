package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.domain.entities.Notification;
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
    private LiveData<List<Notification>> notificationList;

    @Inject
    public GetNotificationViewModel(GetNotificationUseCase getNotificationUseCase) {
        this.getNotificationUseCase = getNotificationUseCase;
        setNotificationList();
    }

    public void setNotificationList() {
        try{
            this.notificationList = getNotificationUseCase.execute();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public LiveData<List<Notification>> getNotificationList() {
        return notificationList;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
    }
}
