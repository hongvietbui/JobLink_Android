package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.api.Pagination;
import com.SE1730.Group3.JobLink.src.data.models.all.JobDTO;
import com.SE1730.Group3.JobLink.src.domain.useCases.JobCreatedByUserUseCase;

import java.io.IOException;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class JobCreatedByUserViewModel extends ViewModel {
    private final JobCreatedByUserUseCase jobCreatedByUserUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public static MutableLiveData<ApiResp<Pagination<JobDTO>>> jobsCreatedResult = new MutableLiveData<>();

    @Inject
    public JobCreatedByUserViewModel(JobCreatedByUserUseCase jobCreatedByUserUseCase) {
        this.jobCreatedByUserUseCase = jobCreatedByUserUseCase;
    }

    public void getJobsCreatedByUser(int pageIndex, int pageSize, String sortBy, boolean isDescending) throws IOException {
        Disposable disposable = jobCreatedByUserUseCase.execute(pageIndex, pageSize, sortBy, isDescending)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
                    jobsCreatedResult.postValue(resp);
                }, error -> {
                    jobsCreatedResult.postValue(new ApiResp<>(error.getMessage(), null));
                });
        disposables.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
