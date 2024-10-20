package com.SE1730.Group3.JobLink.src.data.repositoryImpls;

import com.SE1730.Group3.JobLink.src.data.apis.IJobApi;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiReq;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
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
    public CompletableFuture<ApiResp<String>> getJobs(int pageIndex, int pageSize, String sortBy, boolean isDescending, String filter) throws IOException {
        CompletableFuture<ApiResp<String>> future = new CompletableFuture<>();

        jobApi.getJobs(pageIndex, pageSize, sortBy, isDescending, filter).enqueue(new retrofit2.Callback<ApiResp<String>>() {
            @Override
            public void onResponse(Call<ApiResp<String>> call, Response<ApiResp<String>> response) {
                if (response.isSuccessful()) {
                    future.complete(response.body());
                } else {
                    future.completeExceptionally(new IOException("Failed to fetch jobs"));
                }
            }

            @Override
            public void onFailure(Call<ApiResp<String>> call, Throwable t) {
                future.completeExceptionally(new IOException("Failed to fetch jobs", t));
            }
        });

        return future;
    }

}
