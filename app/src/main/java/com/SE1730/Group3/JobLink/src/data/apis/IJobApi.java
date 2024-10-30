package com.SE1730.Group3.JobLink.src.data.apis;

import com.SE1730.Group3.JobLink.src.data.models.all.JobDTO;
import com.SE1730.Group3.JobLink.src.data.models.all.UserDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.api.Pagination;
import com.SE1730.Group3.JobLink.src.data.models.response.JobOwnerDetailsResp;

import java.util.List;
import java.util.UUID;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface IJobApi {
    @GET("Job/get-jobs")
    Call<ApiResp<Pagination<JobDTO>>> getJobs(
            @Query("pageIndex") int pageIndex,
            @Query("pageSize") int pageSize,
            @Query("sortBy") String sortBy,
            @Query("isDescending") boolean isDescending,
            @Query("filter") String filter
    );

    @GET("job/id")
    Call<ApiResp<JobDTO>> getJobById(@Query("jobId") UUID jobId);

    @GET("job/role")
    Observable<ApiResp<String>> getUserRoleByJobId(@Query("role") UUID jobId);

    @GET("Job/created-by-user")
    Call<ApiResp<Pagination<JobDTO>>> GetJobsCreatedByUser(
            @Query("pageIndex") int pageIndex,
            @Query("pageSize") int pageSize,
            @Query("sortBy") String sortBy,
            @Query("isDescending") boolean isDescending
    );

    @GET("Job/applied-by-user")
    Call<ApiResp<Pagination<JobDTO>>> GetJobsAppliedByUser(
            @Query("pageIndex") int pageIndex,
            @Query("pageSize") int pageSize,
            @Query("sortBy") String sortBy,
            @Query("isDescending") boolean isDescending);

    @GET("Job/apply-job/{jobId}")
    Call<ApiResp<List<UserDTO>>> ListUserApplyJob(@Path("jobId") UUID jobId);

    @GET("Job/job-owner-details/{jobId}")
    Call<ApiResp<JobOwnerDetailsResp>> GetJobOwnerDetails(@Path("jobId") UUID jobId);

    @GET("job/assign/{jobId}")
    Observable<ApiResp<String>> AssignJob(@Path("jobId") UUID jobId, @Query("userId") UUID userId);

    @GET("job/accept/{jobId}/{workerId}")
    Observable<ApiResp<String>> AcceptJob(@Path("jobId") UUID jobId, @Path("workerId") UUID workerId);
}
