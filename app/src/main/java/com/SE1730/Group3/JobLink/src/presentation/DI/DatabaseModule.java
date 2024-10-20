package com.SE1730.Group3.JobLink.src.presentation.DI;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.SE1730.Group3.JobLink.src.domain.dao.IUnitOfWork;
import com.SE1730.Group3.JobLink.src.domain.dao.impl.UnitOfWorkImpl;
import com.SE1730.Group3.JobLink.src.domain.entities.JobLinkDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {
    @Provides
    @Singleton
    public JobLinkDatabase provideAppDatabase(Context context) {
        return Room.databaseBuilder(
                context.getApplicationContext(),
                JobLinkDatabase.class,
                "joblink_database"
        ).build();
    }

    @Provides
    @Singleton
    public IUnitOfWork provideUnitOfWork(JobLinkDatabase database) {
        return new UnitOfWorkImpl(database);
    }

    @Provides
    @Singleton
    public Context provideContext(Application application){
        return application.getApplicationContext();
    }
}
