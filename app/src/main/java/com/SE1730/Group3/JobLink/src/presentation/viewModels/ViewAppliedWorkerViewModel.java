package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.data.models.all.JobWorkerDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.useCases.AcceptWorkerUseCase;
import com.SE1730.Group3.JobLink.src.domain.useCases.RejectWorkerUseCase;
import com.SE1730.Group3.JobLink.src.domain.useCases.ViewAppliedWorkerUseCase;
import com.SE1730.Group3.JobLink.src.presentation.adapters.AppliedWorkerAdapter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class ViewAppliedWorkerViewModel extends ViewModel {
    private final ViewAppliedWorkerUseCase viewAppliedWorkerUseCase;
    private final MutableLiveData<Boolean> isWorkerAccepted = new MutableLiveData<>();
    private final AcceptWorkerUseCase acceptWorkerUseCase;
    private final RejectWorkerUseCase rejectWorkerUseCase;


    private final CompositeDisposable disposables = new CompositeDisposable();

    public final MutableLiveData<ApiResp<List<JobWorkerDTO>>> viewAppliedWorkerResult = new MutableLiveData<>();

    @Inject
    public ViewAppliedWorkerViewModel (ViewAppliedWorkerUseCase viewAppliedWorkerUseCase, AcceptWorkerUseCase acceptWorkerUseCase, RejectWorkerUseCase rejectWorkerUseCase) {
        this.viewAppliedWorkerUseCase = viewAppliedWorkerUseCase;
        this.acceptWorkerUseCase = acceptWorkerUseCase;
        this.rejectWorkerUseCase = rejectWorkerUseCase;
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

    public LiveData<Boolean> getIsWorkerAccepted() {
        return isWorkerAccepted;
    }

    public void acceptWorker(UUID jobId, UUID workerId) throws IOException {
        Disposable disposable = acceptWorkerUseCase.execute(jobId, workerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> isWorkerAccepted.setValue(true),
                        throwable -> isWorkerAccepted.setValue(false)
                );
        disposables.add(disposable);
    }

    public void rejectWorker(UUID jobId, UUID workerId) throws IOException {
        Disposable disposable = rejectWorkerUseCase.execute(jobId, workerId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        response -> isWorkerAccepted.setValue(true),
                        throwable -> isWorkerAccepted.setValue(false)
                );
        disposables.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
