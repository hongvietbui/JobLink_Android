package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.repositories.IJobRepository;

import java.io.IOException;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class RejectWorkerUseCase {
    private final IJobRepository jobRepository;

    @Inject
    public RejectWorkerUseCase(IJobRepository jobRepository){
        this.jobRepository = jobRepository;
    }

    public Observable<ApiResp<String>> execute(UUID jobId,
                                               UUID workerId) throws IOException {
        return jobRepository.rejectWorker(jobId, workerId);
    }
}
