package com.SE1730.Group3.JobLink.src.data.apis;

import com.SE1730.Group3.JobLink.src.data.models.all.JobDTO;
import com.SE1730.Group3.JobLink.src.data.models.all.JobWorkerDTO;
import com.SE1730.Group3.JobLink.src.data.models.all.UserDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiReq;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.api.Pagination;
import com.SE1730.Group3.JobLink.src.data.models.response.JobOwnerDetailsResp;
import com.SE1730.Group3.JobLink.src.data.models.request.CreateJobRequest;

import java.util.List;
import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IJobApi {
    @GET("job/all")
    Observable<ApiResp<Pagination<JobDTO>>> getJobs(
            @Query("pageIndex") Integer pageIndex,
            @Query("pageSize") Integer pageSize,
            @Query("sortBy") String sortBy,
            @Query("isDescending") Boolean isDescending,
            @Query("filter") String filter
    );

    @GET("job/id")
    Observable<ApiResp<JobDTO>> getJobById(@Query("jobId") UUID jobId);

    @GET("job/role")
    Observable<ApiResp<String>> getUserRoleByJobId(@Query("jobId") UUID jobId);

    @GET("job/applied-workers")
    Observable<ApiResp<List<JobWorkerDTO>>> getAppliedWorkersByJobId(
            @Query("jobId") UUID jobId
    );
    @GET("job/user")
    Observable<ApiResp<Pagination<JobDTO>>> GetJobsCreatedByUser(
            @Query("pageIndex") int pageIndex,
            @Query("pageSize") int pageSize,
            @Query("sortBy") String sortBy,
            @Query("isDescending") boolean isDescending
    );

    @GET("job/applied")
    Observable<ApiResp<Pagination<JobDTO>>> GetJobsAppliedByUser(
            @Query("pageIndex") int pageIndex,
            @Query("pageSize") int pageSize,
            @Query("sortBy") String sortBy,
            @Query("isDescending") boolean isDescending);

    @GET("job/applied-workers/{jobId}")
    Observable<ApiResp<List<UserDTO>>> getAppliedUserByJobId(@Path("jobId") UUID jobId);

    @GET("job/job-owner/{jobId}")
    Observable<ApiResp<JobOwnerDetailsResp>> getJobOwnerDetailsByJobId(@Path("jobId") UUID jobId);

    @PATCH("job/assign/{jobId}")
    Observable<ApiResp<String>> assignJob(@Path("jobId") UUID jobId);

    @PATCH("job/accept/{jobId}/{workerId}")
    Observable<ApiResp<String>> acceptAppliedWorker(@Path("jobId") UUID jobId, @Path("workerId") UUID workerId);

    @PATCH("job/reject/{jobId}/{workerId}")
    Observable<ApiResp<String>> rejectAppliedWorker(@Path("jobId") UUID jobId, @Path("workerId") UUID workerId);

    @PATCH("job/complete/{jobId}/{workerId}")
    Observable<ApiResp<String>> completeJob(@Path("jobId") UUID jobId, @Path("workerId") UUID workerId);

    @POST("job")
    Observable<ApiResp<String>> createJob(@Body ApiReq<CreateJobRequest> request);
}
