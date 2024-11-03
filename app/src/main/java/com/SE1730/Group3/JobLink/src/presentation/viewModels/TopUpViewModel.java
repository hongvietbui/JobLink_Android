package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.data.models.all.TopUpDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.useCases.TopUpUseCase;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TopUpViewModel extends ViewModel {
    private final TopUpUseCase topUpUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    public MutableLiveData<ApiResp<List<TopUpDTO>>> topUpResult = new MutableLiveData<>();


    public TopUpViewModel(TopUpUseCase topUpUseCase) {
        this.topUpUseCase = topUpUseCase;
    }

    public void getTopUpHistory(Date fromDate, Date toDate) throws IOException {
        Disposable disposable = topUpUseCase.execute(fromDate, toDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
                    topUpResult.postValue(resp);
                }, error -> {
                    topUpResult.postValue(new ApiResp<>(error.getMessage(), null));
                });
        disposables.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
