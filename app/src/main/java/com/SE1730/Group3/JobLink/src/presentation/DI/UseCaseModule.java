package com.SE1730.Group3.JobLink.src.presentation.DI;

import com.SE1730.Group3.JobLink.src.domain.repositories.IAuthRepository;
import com.SE1730.Group3.JobLink.src.domain.useCases.RegisterUseCase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class UseCaseModule {
    @Provides
    @Singleton
    public static RegisterUseCase providesRegisterUseCase(IAuthRepository authRepository) {
        return new RegisterUseCase(authRepository);
    }
}
