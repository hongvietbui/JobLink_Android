package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.all.JobDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.api.Pagination;
import com.SE1730.Group3.JobLink.src.domain.repositories.IJobRepository;

import java.io.IOException;

import io.reactivex.rxjava3.core.Observable;
import jakarta.inject.Inject;

public class JobCreatedByUserUseCase {
    private final IJobRepository jobRepository;

    @Inject
    public JobCreatedByUserUseCase(IJobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Observable<ApiResp<Pagination<JobDTO>>> execute(int pageIndex, int pageSize, String sortBy, boolean isDescending) throws IOException {
        return jobRepository.getJobsCreatedByUser(pageIndex, pageSize, sortBy, isDescending);
    }
}
