package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.all.JobDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.api.Pagination;
import com.SE1730.Group3.JobLink.src.domain.repositories.IJobRepository;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import jakarta.inject.Inject;

public class GetJobUseCase {
    private final IJobRepository jobRepository;

    @Inject
    public GetJobUseCase(IJobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public CompletableFuture<ApiResp<Pagination<JobDTO>>> execute(int pageIndex, int pageSize, String sortBy, boolean isDescending, String filter) throws IOException {
        return jobRepository.getJobs(pageIndex, pageSize, sortBy, isDescending, filter);
    }
}
