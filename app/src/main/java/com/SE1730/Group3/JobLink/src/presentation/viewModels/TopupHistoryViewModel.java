package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.data.models.all.TopUpDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.useCases.TopupUsecase;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class TopupHistoryViewModel extends ViewModel {
    private final TopupUsecase getTopupUsecase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public MutableLiveData<ApiResp<List<TopUpDTO>>> getTopupHistoryResult = new MutableLiveData<>();
    public TopupHistoryViewModel(TopupUsecase getTopupHistory, TopupUsecase getTopupUsecase) {
        this.getTopupUsecase = getTopupUsecase;
    }

    public void getTopupHistory(Date fromDate, Date toDate) throws IOException {
        Disposable disposable = getTopupUsecase.execute(fromDate, toDate)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
                    //check if the resp status is true or not
                    if (resp.getStatus() == 200) {
                        getTopupHistoryResult.postValue(resp);
                    } else {
                        getTopupHistoryResult.postValue(new ApiResp<List<TopUpDTO>>(resp.getMessage(), null));
                    }
                });
        disposables.add(disposable);
    }

    public void onCleared() {
        super.onCleared();
        disposables.clear();
    }


}
