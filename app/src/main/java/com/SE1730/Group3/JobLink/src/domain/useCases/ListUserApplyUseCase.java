package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.all.UserDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.repositories.IJobRepository;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;
import jakarta.inject.Inject;

public class ListUserApplyUseCase {
    private final IJobRepository jobRepository;

    @Inject
    public ListUserApplyUseCase(IJobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Observable<ApiResp<List<UserDTO>>> execute(UUID jobId) throws IOException {
        return jobRepository.listUserApplyJob(jobId);
    }
}
