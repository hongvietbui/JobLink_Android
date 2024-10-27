package com.SE1730.Group3.JobLink.src.presentation.DI;

import com.SE1730.Group3.JobLink.src.domain.repositories.IUserRepository;
import com.SE1730.Group3.JobLink.src.domain.useCases.LoginUseCase;
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
    public static RegisterUseCase providesRegisterUseCase(IUserRepository authRepository) {
        return new RegisterUseCase(authRepository);
    }

    @Provides
    @Singleton
    public static LoginUseCase providesLoginUseCase(IUserRepository authRepository) {
        return new LoginUseCase(authRepository);
    }
}
