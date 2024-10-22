package com.SE1730.Group3.JobLink.src.data.interceptors;

import android.content.SharedPreferences;

import androidx.annotation.Nullable;

import com.SE1730.Group3.JobLink.src.data.apis.IAuthApi;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiReq;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.data.models.request.RefreshTokenReqDTO;
import com.SE1730.Group3.JobLink.src.data.models.response.AccessTokenRespDTO;
import com.SE1730.Group3.JobLink.src.domain.dao.IUserDAO;

import java.io.IOException;

import javax.inject.Inject;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class TokenAuthenticator implements Authenticator {
    private IUserDAO userDAO;
    private IAuthApi authApi;
    private SharedPreferences sharedPreferences;

    @Inject
    public TokenAuthenticator(IUserDAO userDAO, IAuthApi authApi, SharedPreferences sharedPreferences) {
        this.userDAO = userDAO;
        this.authApi = authApi;
        this.sharedPreferences = sharedPreferences;
    }

    @Nullable
    @Override
    public Request authenticate(@Nullable Route route, Response response) throws IOException {
        if (responseCount(response) >= 2) {
            return null;
        }

        String refreshToken = userDAO.getCurrentUser().getRefreshToken();

        ApiReq<RefreshTokenReqDTO> request = new ApiReq<>(RefreshTokenReqDTO.builder().refreshToken(refreshToken).build());

        retrofit2.Call<ApiResp<AccessTokenRespDTO>> refreshCall = authApi.refreshAccessToken(request);
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
}
