package com.SE1730.Group3.JobLink.src.presentation.DI;

import com.SE1730.Group3.JobLink.src.data.apis.IAuthApi;
import com.squareup.moshi.Moshi;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {
    @Provides
    @Singleton
    public Retrofit provideRetrofit() {
        return new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
    }

    @Provides
    @Singleton
    public IAuthApi provideAuthApi(Retrofit retrofit) {
        return retrofit.create(IAuthApi.class);
    }
}
