package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.response.JobOwnerDetailsResp;
import com.SE1730.Group3.JobLink.src.domain.repositories.IJobRepository;

import java.io.IOException;
import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;
import jakarta.inject.Inject;

public class GetJobWorkerDetailsUsecase {
    private final IJobRepository jobRepository;
    @Inject
    public GetJobWorkerDetailsUsecase(IJobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
    public  Observable<ApiResp<JobOwnerDetailsResp>> execute(UUID JobId) throws IOException {
        return jobRepository.getJobOwnerDetails(JobId);
    }
}
