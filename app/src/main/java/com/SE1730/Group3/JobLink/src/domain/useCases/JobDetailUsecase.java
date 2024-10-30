package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.response.JobAndOwnerDetailsResponse;
import com.SE1730.Group3.JobLink.src.domain.repositories.IJobRepository;

import java.io.IOException;
import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;
import jakarta.inject.Inject;

public class JobDetailUsecase {
    private final IJobRepository jobRepository;
    @Inject
    public JobDetailUsecase(IJobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
    public  Observable<ApiResp<JobAndOwnerDetailsResponse>> execute(UUID JobId) throws IOException {
        return jobRepository.JobDetail(JobId);
    }
}