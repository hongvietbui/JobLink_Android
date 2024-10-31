package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.response.JobOwnerDetailsResp;
import com.SE1730.Group3.JobLink.src.domain.useCases.JobDetailUsecase;

import java.io.IOException;
import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class JobDetailViewModel extends ViewModel {
    private final JobDetailUsecase jobDetailUsecase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    public static MutableLiveData<ApiResp<JobOwnerDetailsResp>> jobsDetailResult = new MutableLiveData<>();

    @Inject
    public JobDetailViewModel(JobDetailUsecase jobDetailUsecase) {
        this.jobDetailUsecase = jobDetailUsecase;
    }

    public void JobDetail(UUID jobId) throws IOException {
        Disposable disposable = jobDetailUsecase.execute(jobId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
                    if (resp != null && resp.getData() != null && resp.getStatus() == 200) {
                        jobsDetailResult.postValue(resp);
                    }
                }, error -> {
                    jobsDetailResult.postValue(new ApiResp<>(error.getMessage(), null));
                });

        disposables.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}