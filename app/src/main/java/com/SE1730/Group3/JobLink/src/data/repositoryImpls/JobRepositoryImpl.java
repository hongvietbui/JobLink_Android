package com.SE1730.Group3.JobLink.src.data.repositoryImpls;

import com.SE1730.Group3.JobLink.src.data.apis.IJobApi;
import com.SE1730.Group3.JobLink.src.data.models.all.JobDTO;
import com.SE1730.Group3.JobLink.src.data.models.all.JobWorkerDTO;
import com.SE1730.Group3.JobLink.src.data.models.all.UserDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiReq;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.api.Pagination;
import com.SE1730.Group3.JobLink.src.data.models.request.CreateJobRequest;
import com.SE1730.Group3.JobLink.src.data.models.response.JobOwnerDetailsResp;
import com.SE1730.Group3.JobLink.src.domain.repositories.IJobRepository;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;
import jakarta.inject.Inject;

public class JobRepositoryImpl implements IJobRepository {
    private final IJobApi jobApi;

    @Inject
    public JobRepositoryImpl(IJobApi jobApi) {
        this.jobApi = jobApi;
    }

    @Override
    public Observable<ApiResp<Pagination<JobDTO>>> getJobs(Integer pageIndex, Integer pageSize, String sortBy, Boolean isDescending, String filter) throws IOException {
        return jobApi.getJobs(pageIndex, pageSize, sortBy, isDescending, filter);
    }

    @Override
    public Observable<ApiResp<JobDTO>> getJobById(UUID jobId) throws IOException {
        return jobApi.getJobById(jobId);
    }

    @Override
    public Observable<ApiResp<List<JobWorkerDTO>>> getAppliedWorkersByJobId(UUID jobId){
        return jobApi.getAppliedWorkersByJobId(jobId);
    }

    public Observable<ApiResp<Pagination<JobDTO>>> getJobsCreatedByUser(Integer pageIndex, Integer pageSize, String sortBy, Boolean isDescending) throws IOException {
        return jobApi.GetJobsCreatedByUser(pageIndex, pageSize, sortBy, isDescending);
    }


    @Override
    public Observable<ApiResp<Pagination<JobDTO>>> getJobsAppliedByUser(Integer pageIndex, Integer pageSize, String sortBy, Boolean isDescending) throws IOException {
        return jobApi.GetJobsAppliedByUser(pageIndex, pageSize, sortBy, isDescending);
    }

    @Override
    public Observable<ApiResp<List<UserDTO>>> listUserApplyJob(UUID jobId) throws IOException {
        return jobApi.getAppliedUserByJobId(jobId);
    }
    @Override
    public Observable<ApiResp<JobOwnerDetailsResp>> getJobOwnerDetails(UUID jobId) throws IOException{
        return jobApi.getJobOwnerDetailsByJobId(jobId);
    }

    @Override
    public Observable<ApiResp<String>> getUserRoleByJobId(UUID jobId) throws IOException {
        return jobApi.getUserRoleByJobId(jobId);
    }

    @Override
    public Observable<ApiResp<String>> acceptWorker(UUID jobId, UUID workerId) throws IOException {
        return jobApi.acceptAppliedWorker(jobId, workerId);
    }

    @Override
    public Observable<ApiResp<String>> rejectWorker(UUID jobId, UUID workerId) throws IOException {
        return jobApi.rejectAppliedWorker(jobId, workerId);
    }

    @Override
    public Observable<ApiResp<JobDTO>> createJob(CreateJobRequest request) {
        ApiReq<CreateJobRequest> apiReq = new ApiReq<>(request);
        return jobApi.createJob(apiReq);
    }

    @Override
    public Observable<ApiResp<String>> assignJob(UUID jobId) throws IOException {
        return jobApi.assignJob(jobId);
    }
}
