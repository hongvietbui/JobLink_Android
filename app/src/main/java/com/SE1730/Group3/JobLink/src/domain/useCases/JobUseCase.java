package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.request.CreateJobRequest;
import com.SE1730.Group3.JobLink.src.domain.repositories.IJobRepository;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class JobUseCase {

    private final IJobRepository jobRepository;

    @Inject
    public JobUseCase(IJobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Observable<ApiResp<String>> createJob(String name, String description, int duration, double price, String avatar, String startTime, String endTime) {

        // Tạo yêu cầu CreateJobRequest
        CreateJobRequest request = CreateJobRequest.builder()
                .name(name)
                .description(description)
                .duration(duration)
                .price(price)
                .avatar(avatar)
                .startTime(startTime)
                .endTime(endTime)
                .build();

        // Gọi phương thức createJob từ jobRepository
        return jobRepository.createJob(request);
    }
}
