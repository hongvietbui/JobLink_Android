package com.SE1730.Group3.JobLink.src.presentation.DI;

import android.content.SharedPreferences;

import com.SE1730.Group3.JobLink.src.domain.dao.IUserDAO;
import com.SE1730.Group3.JobLink.src.domain.repositories.IJobRepository;
import com.SE1730.Group3.JobLink.src.domain.repositories.ITransactionRepository;
import com.SE1730.Group3.JobLink.src.domain.repositories.IUserRepository;
import com.SE1730.Group3.JobLink.src.domain.useCases.GetJobUseCase;
import com.SE1730.Group3.JobLink.src.domain.useCases.GetQRCodeByUserIdUseCase;
import com.SE1730.Group3.JobLink.src.domain.useCases.LoginUseCase;
import com.SE1730.Group3.JobLink.src.domain.useCases.RegisterUseCase;
import com.SE1730.Group3.JobLink.src.domain.useCases.ResetPassUseCase;
import com.SE1730.Group3.JobLink.src.domain.useCases.SendOtpUseCase;
import com.SE1730.Group3.JobLink.src.domain.useCases.VerifyOtpUseCase;

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
    public static GetJobUseCase providesGetJobUseCase(IJobRepository jobRepository) {
        return new GetJobUseCase(jobRepository);
    }
    @Provides
    @Singleton
    public static ResetPassUseCase providesResetPassUseCase(IUserRepository authRepository) {
        return new ResetPassUseCase(authRepository);
    }
    @Provides
    @Singleton
    public static VerifyOtpUseCase providesVerifyOtpUseCase(IUserRepository authRepository) {
        return new VerifyOtpUseCase(authRepository);
    }
    @Provides
    @Singleton
    public static SendOtpUseCase providesSendOtpUseCase(IUserRepository authRepository) {
        return new SendOtpUseCase(authRepository);
    }


    @Provides
    @Singleton
    public static LoginUseCase providesLoginUseCase(IUserRepository authRepository, IUserDAO userDAO, SharedPreferences sharedPreferences) {
        return new LoginUseCase(authRepository, userDAO, sharedPreferences);
    }

    @Provides
    @Singleton
    public static GetQRCodeByUserIdUseCase provideGetQrCodeUseCase(IUserDAO userDao, ITransactionRepository transactionRepository) {
        return new GetQRCodeByUserIdUseCase(userDao, transactionRepository);
    }

}
