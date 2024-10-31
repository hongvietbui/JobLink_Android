package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.all.JobWorkerDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.repositories.IJobRepository;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class ViewAppliedWorkerUseCase {
    private final IJobRepository jobRepository;

    @Inject
    public ViewAppliedWorkerUseCase(IJobRepository jobRepository){
        this.jobRepository = jobRepository;
    }

    public Observable<ApiResp<List<JobWorkerDTO>>> execute(UUID jobId, String accessToken){
        return jobRepository.getAppliedWorkersByJobId(jobId, accessToken);
    }
}
