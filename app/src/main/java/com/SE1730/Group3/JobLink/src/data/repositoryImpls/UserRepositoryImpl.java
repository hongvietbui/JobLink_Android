package com.SE1730.Group3.JobLink.src.data.repositoryImpls;

import com.SE1730.Group3.JobLink.src.data.apis.IAuthApi;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiReq;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.request.ForgetPassReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.LoginReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.OtpReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.RegisterReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.ResetPassDTO;
import com.SE1730.Group3.JobLink.src.data.models.response.LoginRespDTO;
import com.SE1730.Group3.JobLink.src.domain.dao.IUnitOfWork;
import com.SE1730.Group3.JobLink.src.domain.repositories.IUserRepository;

import java.io.IOException;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserRepositoryImpl implements IUserRepository {
    private final IAuthApi authApi;
    private final IUnitOfWork unitOfWork;

    @Inject
    public UserRepositoryImpl(IAuthApi authApi, IUnitOfWork unitOfWork) {
        this.authApi = authApi;
        this.unitOfWork = unitOfWork;
    }

    public Observable<ApiResp<String>> registerUser(RegisterReqDTO request) throws IOException {
        return Observable.create(emmiter -> {
           ApiReq<RegisterReqDTO> apiReq = new ApiReq<>(request);

            authApi.registerUser(apiReq).enqueue(new retrofit2.Callback<ApiResp<String>>() {
                @Override
                public void onResponse(Call<ApiResp<String>> call, Response<ApiResp<String>> response) {
                    if (response.isSuccessful()) {
                        if(!emmiter.isDisposed()){
                            emmiter.onNext(response.body());
                            emmiter.onComplete();
                        }
                    } else {
                        if(!emmiter.isDisposed())
                            emmiter.onError(new IOException("Failed to register user"));
                    }
                }


                @Override
                public void onFailure(Call<ApiResp<String>> call, Throwable t) {
                    if(!emmiter.isDisposed())
                        emmiter.onError(new IOException("Failed to register user"));
                }
            });
        });
    }

    @Override
    public Observable<ApiResp<LoginRespDTO>> loginUser(LoginReqDTO request) throws IOException {
        return Observable.create(emmiter -> {
            authApi.loginUser(new ApiReq<>(request)).enqueue(new Callback<ApiResp<LoginRespDTO>>() {
                @Override
                public void onResponse(Call<ApiResp<LoginRespDTO>> call, Response<ApiResp<LoginRespDTO>> response) {
                    //check if data is null or not
                    if(response.isSuccessful())
                        if(!emmiter.isDisposed())
                            emmiter.onNext(response.body());
                        else
                            emmiter.onError(new IOException("Failed to login user"));
                }

                @Override
                public void onFailure(Call<ApiResp<LoginRespDTO>> call, Throwable throwable) {
                    if(!emmiter.isDisposed())
                        emmiter.onError(new IOException("Failed to login user"));
                }
            });
        });
    }

    @Override
    public Observable<ApiResp<String>> sendOTP(ForgetPassReqDTO request) throws IOException {
        return Observable.create(emitter -> {
            ApiReq<ForgetPassReqDTO> apiReq = new ApiReq<>(request);

            authApi.sendOTP(apiReq).enqueue(new Callback<ApiResp<String>>() {
                @Override
                public void onResponse(Call<ApiResp<String>> call, Response<ApiResp<String>> response) {
                    if (response.isSuccessful()) {
                        if (!emitter.isDisposed()) {
                            emitter.onNext(response.body());
                            emitter.onComplete();
                        }
                    } else {
                        if (!emitter.isDisposed()) {
                            emitter.onError(new IOException("Failed to send OTP"));
                        }
                    }
                }

                @Override
                public void onFailure(Call<ApiResp<String>> call, Throwable t) {
                    if (!emitter.isDisposed()) {
                        emitter.onError(new IOException("Failed to send OTP"));
                    }
                }
            });
        });
    }

    @Override
    public Observable<ApiResp<String>> VerifyOtp(OtpReqDTO request) throws IOException {
        return Observable.create(emitter -> {
            ApiReq<OtpReqDTO> apiReq = new ApiReq<>(request);

            authApi.VerifyOtp(apiReq).enqueue(new Callback<ApiResp<String>>() {
                @Override
                public void onResponse(Call<ApiResp<String>> call, Response<ApiResp<String>> response) {
                    if (response.isSuccessful()) {
                        if (!emitter.isDisposed()) {
                            emitter.onNext(response.body());
                            emitter.onComplete();
                        }
                    } else {
                        if (!emitter.isDisposed()) {
                            emitter.onError(new IOException("Failed to verify OTP"));
                        }
                    }
                }

                @Override
                public void onFailure(Call<ApiResp<String>> call, Throwable t) {
                    if (!emitter.isDisposed()) {
                        emitter.onError(new IOException("Failed to verify OTP"));
                    }
                }
            });
        });
    }

    @Override
    public Observable<ApiResp<String>> ResetPassword(ResetPassDTO request) throws IOException {
        return Observable.create(emitter -> {
            ApiReq<ResetPassDTO> apiReq = new ApiReq<>(request);

            authApi.ResetPassword(apiReq).enqueue(new Callback<ApiResp<String>>() {
                @Override
                public void onResponse(Call<ApiResp<String>> call, Response<ApiResp<String>> response) {
                    if (response.isSuccessful()) {
                        if (!emitter.isDisposed()) {
                            emitter.onNext(response.body());
                            emitter.onComplete();
                        }
                    } else {
                        if (!emitter.isDisposed()) {
                            emitter.onError(new IOException("Failed to reset password"));
                        }
                    }
                }

                @Override
                public void onFailure(Call<ApiResp<String>> call, Throwable t) {
                    if (!emitter.isDisposed()) {
                        emitter.onError(new IOException("Failed to reset password"));
                    }
                }
            });
        });
    }
}
