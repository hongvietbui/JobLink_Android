package com.SE1730.Group3.JobLink.src.presentation.viewModels;

import retrofit2.HttpException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.SE1730.Group3.JobLink.src.data.models.all.JobDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.useCases.JobUseCase;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

@HiltViewModel
public class JobViewModel extends ViewModel {

    private final JobUseCase jobUseCase;
    private final MutableLiveData<ApiResp<JobDTO>> createJobResult = new MutableLiveData<>();
    private final CompositeDisposable disposables = new CompositeDisposable();

    public LiveData<ApiResp<JobDTO>> getCreateJobResult() {
        return createJobResult;
    }

    @Inject
    public JobViewModel(JobUseCase jobUseCase) {
        this.jobUseCase = jobUseCase;
    }

    public void createJob(String name, String description, int duration, double price, String avatar, String startTime, String endTime) {
        disposables.add(jobUseCase.createJob(name, description, duration, price, avatar, startTime, endTime)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        result -> createJobResult.setValue(result),
                        throwable -> {
                            if (throwable instanceof HttpException) {
                                HttpException httpException = (HttpException) throwable;
                                int statusCode = httpException.code();
                                if (statusCode == 402) {
                                    // Tạo ApiResp với thông báo lỗi nạp tiền
                                    ApiResp<JobDTO> paymentRequiredResponse = new ApiResp<>(402, "Insufficient funds. Please recharge your account.", null);
                                    createJobResult.setValue(paymentRequiredResponse);
                                } else {
                                    // Các mã lỗi HTTP khác
                                    createJobResult.setValue(new ApiResp<>(statusCode, "An error occurred", null));
                                }
                            } else {
                                // Xử lý các ngoại lệ khác (không phải HttpException)
                                createJobResult.setValue(new ApiResp<>(500, "An unknown error occurred", null));
                            }
                        }
                ));
    }





    @Override
    protected void onCleared() {
        super.onCleared();
        disposables.clear();
    }
}
