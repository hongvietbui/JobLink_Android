package com.SE1730.Group3.JobLink.src.presentation.DI;

import android.content.SharedPreferences;

import com.SE1730.Group3.JobLink.src.data.apis.IAuthApi;
import com.SE1730.Group3.JobLink.src.data.apis.IJobApi;
import com.SE1730.Group3.JobLink.src.data.interceptors.AuthInterceptor;
import com.SE1730.Group3.JobLink.src.presentation.adapters.LocalDateJsonAdapter;
import com.squareup.moshi.Moshi;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {
    @Provides
    @Singleton
    public Retrofit provideRetrofit(SharedPreferences sharedPreferences) {
        String token = sharedPreferences.getString("accessToken", "");

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .addInterceptor(new AuthInterceptor(token))
                .build();

        Moshi moshi = new Moshi.Builder()
                .add(new LocalDateJsonAdapter())
                .build();

        return new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .build();
    }

    @Provides
    @Singleton
    public IAuthApi provideAuthApi(Retrofit retrofit) {
        return retrofit.create(IAuthApi.class);
    }

    @Provides
    @Singleton
    public IJobApi provideJobApi(Retrofit retrofit) {
        return retrofit.create(IJobApi.class);
    }
}
