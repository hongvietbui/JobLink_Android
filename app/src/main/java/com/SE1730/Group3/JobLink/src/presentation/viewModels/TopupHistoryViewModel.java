package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.data.models.all.TopUpDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class TopupHistoryViewModel extends ViewModel {
    private final TopupUsecase topUpUsecase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public final MutableLiveData<ApiResp<List<TopUpDTO>>> topUpResult = new MutableLiveData<>();

    @Inject
    public TopupHistoryViewModel(TopupUsecase topUpUsecase) {
        this.topUpUsecase = topUpUsecase;
    }

    public void TopUpHistory( Date fromDate, Date toDate)
            throws IOException {
        Disposable disposable = topUpUsecase.execute(fromDate, toDate)
                .subscribeOn(Schedulers.io())
                .subscribe(resp -> {
                    topUpResult.postValue(resp);
                }, error -> {
                    topUpResult.postValue(new ApiResp<>(error.getMessage(), null));
                });
        disposables.add(disposable);
    }

    public void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
