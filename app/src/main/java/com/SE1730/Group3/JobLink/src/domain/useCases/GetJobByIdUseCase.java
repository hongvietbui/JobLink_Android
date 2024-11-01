package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.all.JobDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.api.Pagination;
import com.SE1730.Group3.JobLink.src.domain.repositories.IJobRepository;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import io.reactivex.rxjava3.core.Observable;
import jakarta.inject.Inject;

public class GetJobByIdUseCase {
    private final IJobRepository jobRepository;

    @Inject
    public GetJobByIdUseCase(IJobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Observable<ApiResp<JobDTO>> execute(UUID jobId) throws IOException {
        return jobRepository.getJobById(jobId);
    }
}
