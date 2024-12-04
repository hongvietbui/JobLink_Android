package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.repositories.IJobRepository;

import java.io.IOException;
import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;

public class CompleteJobUseCase {
    private final IJobRepository jobRepository;

    public CompleteJobUseCase(IJobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Observable<ApiResp<String>> execute(UUID jobId) throws IOException {
        return jobRepository.completeJob(jobId);
    }
}