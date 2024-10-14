package com.SE1730.Group3.JobLink.src.presentation.DI;

import com.SE1730.Group3.JobLink.src.data.apis.IAuthApi;
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
    public Retrofit provideRetrofit() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
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
}
