package com.SE1730.Group3.JobLink.src.data.repositoryImpls;

import android.util.Log;

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
import java.util.concurrent.CompletableFuture;

import io.reactivex.rxjava3.core.Observable;
import jakarta.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
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
                if (response.isSuccessful() && response.body() != null) {
                    future.complete(response.body());
                } else {
                    String errorMessage = "Failed to fetch jobs. Status Code: " + response.code() + ", Message: " + response.message();
                    Log.e("JobRepositoryImpl", errorMessage);
                    future.completeExceptionally(new IOException(errorMessage));
                }
            }

            @Override
            public void onFailure(Call<ApiResp<Pagination<JobDTO>>> call, Throwable t) {
                Log.e("JobRepositoryImpl", "Network request failed", t);
                future.completeExceptionally(new IOException("Network request failed", t));
            }
        });

        return future;
    }

    @Override
    public Observable<ApiResp<JobDTO>> getJobById(UUID jobId) throws IOException {
        return jobApi.getJobById(jobId);
    }

    @Override
    public Observable<ApiResp<List<JobWorkerDTO>>> getAppliedWorkersByJobId(UUID jobId, String accessToken){
        return jobApi.getAppliedWorkersByJobId(jobId, accessToken);
    }

    public Observable<ApiResp<Pagination<JobDTO>>> getJobsCreatedByUser(int pageIndex, int pageSize, String sortBy, boolean isDescending) throws IOException {
        return Observable.create(emitter -> {

            jobApi.GetJobsCreatedByUser(pageIndex, pageSize, sortBy, isDescending).enqueue(new Callback<ApiResp<Pagination<JobDTO>>>() {
                @Override
                public void onResponse(Call<ApiResp<Pagination<JobDTO>>> call, Response<ApiResp<Pagination<JobDTO>>> response) {
                    if (response.isSuccessful()) {
                        if (!emitter.isDisposed()) {
                            emitter.onNext(response.body());
                            emitter.onComplete();
                        }
                    } else {
                        String errorMessage = "Failed to fetch jobs created by user. Status Code: " + response.code() + ", Message: " + response.message();
                        Log.e("JobRepositoryImpl", errorMessage);

                        // Log error body if present
                        if (response.errorBody() != null) {
                            try {
                                String errorResponse = response.errorBody().string();
                                Log.e("JobRepositoryImpl", "Error Response: " + errorResponse);
                            } catch (IOException e) {
                                Log.e("JobRepositoryImpl", "Failed to read error response", e);
                            }
                        }

                        if (!emitter.isDisposed()) {
                            emitter.onError(new IOException(errorMessage));
                        }
                    }
                }

                @Override
                public void onFailure(Call<ApiResp<Pagination<JobDTO>>> call, Throwable t) {
                    Log.e("JobRepositoryImpl", "Network request failed", t);
                    if (!emitter.isDisposed()) {
                        emitter.onError(new IOException("Network request failed", t));
                    }
                }
            });
        });
    }


    @Override
    public Observable<ApiResp<Pagination<JobDTO>>> getJobsAppliedByUser(int pageIndex, int pageSize, String sortBy, boolean isDescending) throws IOException {
        return Observable.create(emitter -> {
            jobApi.GetJobsAppliedByUser(pageIndex, pageSize, sortBy, isDescending).enqueue(new Callback<ApiResp<Pagination<JobDTO>>>() {
                @Override
                public void onResponse(Call<ApiResp<Pagination<JobDTO>>> call, Response<ApiResp<Pagination<JobDTO>>> response) {
                    if (response.isSuccessful()) {
                        if (!emitter.isDisposed()) {
                            emitter.onNext(response.body());
                            emitter.onComplete();
                        }
                    } else {
                        String errorMessage = "Failed to fetch jobs applied by user. Status Code: " + response.code() + ", Message: " + response.message();
                        Log.e("JobRepositoryImpl", errorMessage);
                        if (!emitter.isDisposed()) {
                            emitter.onError(new IOException(errorMessage));
                        }
                    }
                }

                @Override
                public void onFailure(Call<ApiResp<Pagination<JobDTO>>> call, Throwable t) {
                    Log.e("JobRepositoryImpl", "Network request failed", t);
                    if (!emitter.isDisposed()) {
                        emitter.onError(new IOException("Network request failed", t));
                    }
                }
            });
        });
    }

    @Override
    public Observable<ApiResp<List<UserDTO>>> listUserApplyJob(UUID jobId) throws IOException {
        return Observable.create(emitter -> {
            jobApi.ListUserApplyJob(jobId).enqueue(new Callback<ApiResp<List<UserDTO>>>() {
                @Override
                public void onResponse(Call<ApiResp<List<UserDTO>>> call, Response<ApiResp<List<UserDTO>>> response) {
                    if (response.isSuccessful()) {
                        if (!emitter.isDisposed()) {
                            emitter.onNext(response.body());
                            emitter.onComplete();
                        }
                    } else {
                        String errorMessage = "Failed to fetch users who applied for job. Status Code: " + response.code() + ", Message: " + response.message();
                        Log.e("JobRepositoryImpl", errorMessage);
                        if (!emitter.isDisposed()) {
                            emitter.onError(new IOException(errorMessage));
                        }
                    }
                }

                @Override
                public void onFailure(Call<ApiResp<List<UserDTO>>> call, Throwable t) {
                    Log.e("JobRepositoryImpl", "Network request failed", t);
                    if (!emitter.isDisposed()) {
                        emitter.onError(new IOException("Network request failed", t));
                    }
                }
            });
        });
    }
    @Override
    public Observable<ApiResp<JobOwnerDetailsResp>> getJobOwnerDetails(UUID jobId) throws IOException{
        return jobApi.GetJobOwnerDetails(jobId);
//        return Observable.create(emitter -> {
//            jobApi.GetJobOwnerDetails(jobId).enqueue(new Callback<ApiResp<JobOwnerDetailsResp>>() {
//                @Override
//                public void onResponse(Call<ApiResp<JobOwnerDetailsResp>> call, Response<ApiResp<JobOwnerDetailsResp>> response) {
//                    if(response.isSuccessful()){
//                        if(!emitter.isDisposed()){
//                            emitter.onNext(response.body());
//                            emitter.onComplete();
//                        }
//                    }else{
//                        String errorMessage = "Failed to fetch job and user detail. Status Code: " + response.code() + ", Message: " + response.message();
//                        if (!emitter.isDisposed()) {
//                            emitter.onError(new IOException(errorMessage));
//                        }
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ApiResp<JobOwnerDetailsResp>> call, Throwable throwable) {
//                    if (!emitter.isDisposed()) {
//                        emitter.onError(new IOException("Network request failed", throwable));
//                    }
//                }
//            });
//        });
    }

    @Override
    public Observable<ApiResp<String>> getUserRoleByJobId(UUID jobId) throws IOException {
        return jobApi.getUserRoleByJobId(jobId);
    }

    @Override
    public Observable<ApiResp<String>> acceptWorker(UUID jobId, UUID workerId) throws IOException {
        return jobApi.AcceptWorker(jobId, workerId);
    }

    @Override
    public Observable<ApiResp<String>> rejectWorker(UUID jobId, UUID workerId) throws IOException {
        return jobApi.RejectWorker(jobId, workerId);
    }

    @Override
    public Observable<ApiResp<String>> createJob(CreateJobRequest request) {
        ApiReq<CreateJobRequest> apiReq = new ApiReq<>(request);
        return jobApi.createJob(apiReq);
    }

    @Override
    public Observable<ApiResp<String>> assignJob(UUID jobId) throws IOException {
        return jobApi.assignJob(jobId);
    }
}
