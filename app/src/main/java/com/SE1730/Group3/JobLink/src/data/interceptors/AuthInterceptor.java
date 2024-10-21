package com.SE1730.Group3.JobLink.src.data.interceptors;

import java.io.IOException;

import lombok.AllArgsConstructor;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

@AllArgsConstructor
public class AuthInterceptor implements Interceptor {
    private String accessToken;

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder()
                .header("Authorization", "Bearer " + accessToken);
        Request newRequest = builder.build();
        return chain.proceed(newRequest);
    }
}
