package com.SE1730.Group3.JobLink.src.domain.repositories;
import com.SE1730.Group3.JobLink.src.data.models.all.JobWorkerDTO;

import com.SE1730.Group3.JobLink.src.data.models.all.JobDTO;
import com.SE1730.Group3.JobLink.src.data.models.all.JobWorkerDTO;
import com.SE1730.Group3.JobLink.src.data.models.all.UserDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.api.Pagination;
import com.SE1730.Group3.JobLink.src.data.models.request.CreateJobRequest;
import com.SE1730.Group3.JobLink.src.data.models.response.JobOwnerDetailsResp;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import io.reactivex.rxjava3.core.Observable;

public interface IJobRepository {
    Observable<ApiResp<Pagination<JobDTO>>> getJobs(Integer pageIndex, Integer pageSize, String sortBy, Boolean isDescending, String filter) throws IOException;

    Observable<ApiResp<JobDTO>> getJobById(UUID jobId) throws IOException;
    Observable<ApiResp<Pagination<JobDTO>>> getJobsCreatedByUser(Integer pageIndex, Integer pageSize, String sortBy, Boolean isDescending) throws IOException;

    Observable<ApiResp<Pagination<JobDTO>>> getJobsAppliedByUser(Integer pageIndex, Integer pageSize, String sortBy, Boolean isDescending) throws IOException;

    Observable<ApiResp<List<UserDTO>>> listUserApplyJob(UUID jobId) throws IOException;

    Observable<ApiResp<JobOwnerDetailsResp>> getJobOwnerDetails(UUID jobId) throws IOException;

    Observable<ApiResp<String>> getUserRoleByJobId(UUID jobId) throws IOException;

    Observable<ApiResp<JobDTO>> createJob(CreateJobRequest request);
    Observable<ApiResp<String>> assignJob(UUID jobId) throws IOException;

    Observable<ApiResp<List<JobWorkerDTO>>> getAppliedWorkersByJobId(UUID jobId);
    Observable<ApiResp<String>> acceptWorker(UUID jobId, UUID workerId) throws IOException;
    Observable<ApiResp<String>> rejectWorker(UUID jobId, UUID workerId) throws IOException;
    Observable<ApiResp<String>> completeJob(UUID jobId) throws IOException;
}
