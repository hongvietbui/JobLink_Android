package com.SE1730.Group3.JobLink.src.data.interceptors;

import android.content.SharedPreferences;
import android.util.Log;

import java.io.IOException;

import javax.inject.Inject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@Data
@AllArgsConstructor
public class AuthInterceptor implements Interceptor {
    private SharedPreferences sharedPreferences;

    @Override
    public Response intercept(Chain chain) throws IOException {
        String accessToken = sharedPreferences.getString("accessToken", "");

        if(!accessToken.isEmpty()){
            Request originalRequest = chain.request();

            Request.Builder builder = originalRequest.newBuilder()
                    .header("Authorization", "Bearer " + accessToken);
            Request newRequest = builder.build();
            return chain.proceed(newRequest);
        }

        return chain.proceed(chain.request());
    }
}
