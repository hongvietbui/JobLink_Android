package com.SE1730.Group3.JobLink.src.domain.useCases;

import android.content.SharedPreferences;

import com.SE1730.Group3.JobLink.src.data.apis.IJobApi;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.repositories.IJobRepository;

import java.io.IOException;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class GetUserRoleOfJobUserCase {
    private final IJobRepository jobRepository;

    @Inject
    public GetUserRoleOfJobUserCase(IJobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    public Observable<ApiResp<String>> execute(UUID jobId) throws IOException {
        return jobRepository.getUserRoleByJobId(jobId);
    }
}
