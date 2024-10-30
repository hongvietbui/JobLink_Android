package com.SE1730.Group3.JobLink.src.domain.repositories;
import com.SE1730.Group3.JobLink.src.data.models.all.JobWorkerDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import io.reactivex.rxjava3.core.Observable;

public interface IJobRepository
{
    CompletableFuture<ApiResp<String>> getJobs(int pageIndex, int pageSize, String sortBy, boolean isDescending, String filter) throws IOException;
    Observable<ApiResp<List<JobWorkerDTO>>> getAppliedWorkersByJobId(UUID jobId, String accessToken);
}
