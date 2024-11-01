package com.SE1730.Group3.JobLink.src.presentation.DI;

import android.content.Context;
import android.content.SharedPreferences;

import com.SE1730.Group3.JobLink.src.data.apis.IAuthApi;
import com.SE1730.Group3.JobLink.src.data.apis.IJobApi;
import com.SE1730.Group3.JobLink.src.data.apis.ITransactionApi;
import com.SE1730.Group3.JobLink.src.data.apis.IUserApi;
import com.SE1730.Group3.JobLink.src.data.interceptors.AuthInterceptor;
import com.SE1730.Group3.JobLink.src.data.interceptors.TokenAuthenticator;
import com.SE1730.Group3.JobLink.src.domain.converters.BigDecimalConverter;
import com.SE1730.Group3.JobLink.src.domain.converters.LocalDateConverter;
import com.SE1730.Group3.JobLink.src.domain.converters.LocalDateTimeConverter;
import com.SE1730.Group3.JobLink.src.domain.converters.UUIDJsonConverter;
import com.SE1730.Group3.JobLink.src.domain.dao.IUserDAO;
import com.squareup.moshi.Moshi;

import javax.inject.Provider;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.moshi.MoshiConverterFactory;

@Module
@InstallIn(SingletonComponent.class)
public class NetworkModule {
    @Provides
    @Singleton
    public Moshi provideMoshi(){
        Moshi moshi = new Moshi.Builder()
                .add(new LocalDateConverter())
                .add(new LocalDateTimeConverter())
                .add(new UUIDJsonConverter())
                .add(new BigDecimalConverter())
                .build();
        return moshi;
    }

    @Provides
    @Singleton
    public Retrofit provideRetrofit(SharedPreferences sharedPreferences, Moshi moshi, IUserDAO userDAO, Provider<IAuthApi> authApiProvider, Context context) {
        String token = sharedPreferences.getString("accessToken", "");

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .readTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .writeTimeout(30, java.util.concurrent.TimeUnit.SECONDS)
                .addInterceptor(new AuthInterceptor(sharedPreferences))
                .authenticator(new TokenAuthenticator(userDAO, authApiProvider, sharedPreferences, context))
                .build();

        return new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/api/")
                .client(okHttpClient)
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
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

    @Provides
    @Singleton
    public IUserApi provideUserApi(Retrofit retrofit) {
        return retrofit.create(IUserApi.class);
    }

    @Provides
    @Singleton
    public ITransactionApi provideTransactionApi(Retrofit retrofit) {
        return retrofit.create(ITransactionApi.class);
    }
}
