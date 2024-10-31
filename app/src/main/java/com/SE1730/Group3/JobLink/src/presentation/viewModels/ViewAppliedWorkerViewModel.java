package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.data.models.all.JobWorkerDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.useCases.ViewAppliedWorkerUseCase;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ViewAppliedWorkerViewModel extends ViewModel {
    private final ViewAppliedWorkerUseCase viewAppliedWorkerUseCase;

    private final CompositeDisposable disposables = new CompositeDisposable();

    public final MutableLiveData<ApiResp<List<JobWorkerDTO>>> viewAppliedWorkerResult = new MutableLiveData<>();

    @Inject
    public ViewAppliedWorkerViewModel (ViewAppliedWorkerUseCase viewAppliedWorkerUseCase){
        this.viewAppliedWorkerUseCase = viewAppliedWorkerUseCase;
    }

    public void ViewAppliedWorker(UUID jobId, String accessToken)
            throws IOException
    {
        Disposable disposable = viewAppliedWorkerUseCase.execute(jobId, accessToken)
                .subscribeOn(Schedulers.io())
                .subscribe(resp -> {
                    viewAppliedWorkerResult.postValue(resp);
                }, error -> {
                    viewAppliedWorkerResult.postValue(new ApiResp<List<JobWorkerDTO>>(error.getMessage(), null));
                });
        disposables.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
