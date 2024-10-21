package com.SE1730.Group3.JobLink.src.data.repositoryImpls;

import android.util.Log;

import com.SE1730.Group3.JobLink.src.data.apis.IJobApi;
import com.SE1730.Group3.JobLink.src.data.models.all.JobDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiReq;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.api.Pagination;
import com.SE1730.Group3.JobLink.src.data.models.listjob.ListJobReqDTO;
import com.SE1730.Group3.JobLink.src.domain.repositories.IJobRepository;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

import jakarta.inject.Inject;

import retrofit2.Call;
import retrofit2.Response;

public class JobRepositoryImpl implements IJobRepository {
    private final IJobApi jobApi;

    @Inject
    public JobRepositoryImpl(IJobApi jobApi) {
        this.jobApi = jobApi;
    }

    @Override
    public CompletableFuture<ApiResp<Pagination<JobDTO>>> getJobs(int pageIndex, int pageSize, String sortBy, boolean isDescending, String filter) throws IOException {
        CompletableFuture<ApiResp<Pagination<JobDTO>>> future = new CompletableFuture<>();

        jobApi.getJobs(pageIndex, pageSize, sortBy, isDescending, filter).enqueue(new retrofit2.Callback<ApiResp<Pagination<JobDTO>>>() {
            @Override
            public void onResponse(Call<ApiResp<Pagination<JobDTO>>> call, Response<ApiResp<Pagination<JobDTO>>> response) {
                if (response.isSuccessful()) {
                    future.complete(response.body());
                } else {
                    future.completeExceptionally(new IOException("Failed to fetch jobs"));
                }
            }

            @Override
            public void onFailure(Call<ApiResp<Pagination<JobDTO>>> call, Throwable t) {
                Log.e("JobRepositoryImpl", "API call failed", t);
                future.completeExceptionally(new IOException("Failed to fetch jobs", t));
            }
        });

        return future;
    }

}
