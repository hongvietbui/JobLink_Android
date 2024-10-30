package com.SE1730.Group3.JobLink.src.data.repositoryImpls;

import com.SE1730.Group3.JobLink.src.data.apis.IAuthApi;
import com.SE1730.Group3.JobLink.src.data.apis.IUserApi;
import com.SE1730.Group3.JobLink.src.data.models.all.NotificationDTO;
import com.SE1730.Group3.JobLink.src.data.models.all.TopUpDTO;
import com.SE1730.Group3.JobLink.src.data.models.all.UserDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiReq;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.request.ForgetPassReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.ChangePassReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.LoginReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.OtpReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.RegisterReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.ResetPassDTO;
import com.SE1730.Group3.JobLink.src.data.models.request.TopupReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.response.LoginRespDTO;
import com.SE1730.Group3.JobLink.src.domain.dao.IUnitOfWork;
import com.SE1730.Group3.JobLink.src.domain.repositories.IUserRepository;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserRepositoryImpl implements IUserRepository {


    private final IAuthApi authApi;
    private final IUserApi userApi;
    private final IUnitOfWork unitOfWork;

    @Inject
    public UserRepositoryImpl(IAuthApi authApi, IUserApi userApi, IUnitOfWork unitOfWork) {
        this.authApi = authApi;
        this.unitOfWork = unitOfWork;
        this.userApi = userApi;
    }

    public Observable<ApiResp<String>> registerUser(RegisterReqDTO request) throws IOException {
        return Observable.create(emmiter -> {
            ApiReq<RegisterReqDTO> apiReq = new ApiReq<>(request);

            authApi.registerUser(apiReq).enqueue(new retrofit2.Callback<ApiResp<String>>() {
                @Override
                public void onResponse(Call<ApiResp<String>> call, Response<ApiResp<String>> response) {
                    if (response.isSuccessful()) {
                        if (!emmiter.isDisposed()) {
                            emmiter.onNext(response.body());
                            emmiter.onComplete();
                        }
                    } else {
                        if (!emmiter.isDisposed())
                            emmiter.onError(new IOException("Failed to register user"));
                    }
                }


                @Override
                public void onFailure(Call<ApiResp<String>> call, Throwable t) {
                    if (!emmiter.isDisposed())
                        emmiter.onError(new IOException("Failed to register user"));
                }
            });
        });
    }

    @Override
    public Observable<ApiResp<LoginRespDTO>> loginUser(LoginReqDTO request) throws IOException {
        return authApi.loginUser(new ApiReq<>(request));
    }

    @Override
    public Observable<ApiResp<Boolean>> logoutUser() throws IOException {
        throw new IOException("Not implemented");
        //Todo: delete user info
//        return new Observable.create(emmiter -> {
//            //delete user info
//            unitOfWork.getAuthDao().deleteToken();
//            emmiter.onNext(new ApiResp<>(200, "Logout successful", true));
//            emmiter.onComplete();
//        });
    }

    @Override
    public Observable<ApiResp<UserDTO>> getCurrentUser() throws IOException {
        return userApi.getCurrentUser();
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

    @Override
    public Observable<ApiResp<List<NotificationDTO>>> getNotificationsForCurrentUser() throws IOException {
        return userApi.getUserNotification();
    }

    @Override
    public Observable<ApiResp<List<TopUpDTO>>> getUserTransaction(TopupReqDTO request) throws IOException {
        return userApi.getUserTransaction(new ApiReq<>(request));
    }


    public Observable<ApiResp<String>> changePassUser(ChangePassReqDTO request) throws IOException {
        return authApi.changePassUser(new ApiReq<>(request));
    }

}
