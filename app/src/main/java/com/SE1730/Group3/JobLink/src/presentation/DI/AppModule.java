package com.SE1730.Group3.JobLink.src.presentation.DI;

import com.SE1730.Group3.JobLink.src.data.apis.IAuthApi;
import com.SE1730.Group3.JobLink.src.data.repositoryImpls.AuthRepositoryImpl;
import com.SE1730.Group3.JobLink.src.domain.repositories.IAuthRepository;
import com.SE1730.Group3.JobLink.src.domain.useCases.RegisterUseCase;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;
import javax.inject.Singleton;

@Module
@InstallIn(SingletonComponent.class)
public class AppModule {
    @Provides
    @Singleton
    public IAuthRepository provideAuthRepository(IAuthApi authApi) {
        return new AuthRepositoryImpl(authApi);
    }

}
