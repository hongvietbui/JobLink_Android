package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.repositories.IJobRepository;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class AcceptWorkerUseCase {
    private final IJobRepository jobRepository;

    @Inject
    public AcceptWorkerUseCase(IJobRepository jobRepository){
        this.jobRepository = jobRepository;
    }

    public Observable<ApiResp<String>> execute(String accessToken,
                                               String jobId,
                                               String workerId) throws IOException {
        return jobRepository.acceptWorker(accessToken, jobId, workerId);
    }
}
