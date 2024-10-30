package com.SE1730.Group3.JobLink.src.presentation.DI;

import com.SE1730.Group3.JobLink.src.data.apis.IAuthApi;
import com.SE1730.Group3.JobLink.src.data.apis.ITransactionApi;
import com.SE1730.Group3.JobLink.src.data.apis.IUserApi;
import com.SE1730.Group3.JobLink.src.data.apis.IJobApi;
import com.SE1730.Group3.JobLink.src.data.repositoryImpls.JobRepositoryImpl;
import com.SE1730.Group3.JobLink.src.data.repositoryImpls.UserRepositoryImpl;
import com.SE1730.Group3.JobLink.src.domain.dao.IUnitOfWork;
import com.SE1730.Group3.JobLink.src.domain.repositories.IJobRepository;
import com.SE1730.Group3.JobLink.src.data.repositoryImpls.TransactionRepositoryImpl;
import com.SE1730.Group3.JobLink.src.domain.repositories.ITransactionRepository;
import com.SE1730.Group3.JobLink.src.domain.repositories.IUserRepository;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;

import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModule {

    @Provides
    @Singleton
    public IUserRepository provideAuthRepository(IAuthApi authApi, IUserApi userApi, IUnitOfWork unitOfWork) {
        return new UserRepositoryImpl(authApi, userApi, unitOfWork);
    }

    @Provides
    @Singleton
    public IJobRepository provideJobRepository(IJobApi jobApi) {
        return new JobRepositoryImpl(jobApi);
    }

    @Provides
    @Singleton
    public ITransactionRepository provideTransactionRepository(ITransactionApi transactionApi) {
        return new TransactionRepositoryImpl(transactionApi);
    }
}
