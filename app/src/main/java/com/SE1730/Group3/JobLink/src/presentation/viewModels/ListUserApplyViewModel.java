package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.data.models.all.UserDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.useCases.ListUserApplyUseCase;

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
public class ListUserApplyViewModel extends ViewModel {
    private final ListUserApplyUseCase listUserApplyJobUseCase;
    private final CompositeDisposable disposables = new CompositeDisposable();
    public static MutableLiveData<ApiResp<List<UserDTO>>> usersAppliedResult = new MutableLiveData<>();

    @Inject
    public ListUserApplyViewModel(ListUserApplyUseCase listUserApplyJobUseCase) {
        this.listUserApplyJobUseCase = listUserApplyJobUseCase;
    }

    public void getUsersAppliedByJob(UUID jobId) throws IOException {
        Disposable disposable = listUserApplyJobUseCase.execute(jobId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(resp -> {
                    usersAppliedResult.postValue(resp);
                }, error -> {
                    usersAppliedResult.postValue(new ApiResp<>(error.getMessage(), null));
                });
        disposables.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
