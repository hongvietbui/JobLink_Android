package com.SE1730.Group3.JobLink.src.data.interceptors;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.SE1730.Group3.JobLink.src.data.apis.IAuthApi;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiReq;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.request.RefreshTokenReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.response.AccessTokenRespDTO;
import com.SE1730.Group3.JobLink.src.domain.dao.IUserDAO;
import com.SE1730.Group3.JobLink.src.domain.entities.User;
import com.SE1730.Group3.JobLink.src.presentation.activities.LoginActivity;

import java.io.IOException;

import javax.inject.Inject;
import javax.inject.Provider;

import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {
    private IUserDAO userDAO;
    private Provider<IAuthApi> authApiProvider;
    private SharedPreferences sharedPreferences;
    private final Context context;

    @Inject
    public TokenAuthenticator(IUserDAO userDAO, Provider<IAuthApi> authApiProvider, SharedPreferences sharedPreferences, Context context) {
        this.userDAO = userDAO;
        this.authApiProvider = authApiProvider;
        this.sharedPreferences = sharedPreferences;
        this.context = context;
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, Response response) throws IOException {
        if (responseCount(response) >= 2) {
            return null;
        }

        User user;
        try {
            user = userDAO.getCurrentUser().subscribeOn(Schedulers.io()).blockingGet();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        ApiReq<RefreshTokenReqDTO> request = new ApiReq<>(RefreshTokenReqDTO.builder().refreshToken(user.getRefreshToken()).build());

        retrofit2.Call<ApiResp<AccessTokenRespDTO>> refreshCall = authApiProvider.get().refreshAccessToken(request);
        try {
            retrofit2.Response<ApiResp<AccessTokenRespDTO>> accessTokenResp = refreshCall.execute();
            //check if the response is successful
            if (accessTokenResp.isSuccessful() && accessTokenResp.body() != null && accessTokenResp.body().getStatus() >= 200 && accessTokenResp.body().getStatus() < 300) {
                String newAccessToken = accessTokenResp.body().getData().getAccessToken();
                sharedPreferences.edit().putString("accessToken", newAccessToken).apply();

                return response.request().newBuilder()
                        .header("Authorization", "Bearer " + newAccessToken)
                        .build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return null;
    }

    private int responseCount(Response response){
        int count = 1;
        while((response = response.priorResponse()) != null){
            count++;
        }
        return count;
    }

    private void handleRefreshTokenExpired() {
        // Xóa dữ liệu người dùng
        sharedPreferences.edit().clear().apply();

        // Tạo Intent để đưa người dùng về LoginActivity
        Intent intent = new Intent(context, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

}
