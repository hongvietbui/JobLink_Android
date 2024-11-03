package com.SE1730.Group3.JobLink.src.presentation.DI;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.SE1730.Group3.JobLink.src.domain.dao.IMessageDAO;
import com.SE1730.Group3.JobLink.src.domain.dao.INotificationDAO;
import com.SE1730.Group3.JobLink.src.domain.dao.IUnitOfWork;
import com.SE1730.Group3.JobLink.src.domain.dao.IUserDAO;
import com.SE1730.Group3.JobLink.src.domain.dao.impl.UnitOfWorkImpl;
import com.SE1730.Group3.JobLink.src.domain.entities.JobLinkDatabase;
import com.SE1730.Group3.JobLink.src.domain.migrations.UpdateDatabase;

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
        ).addMigrations(UpdateDatabase.MIGRATION_1_2)
                .addMigrations(UpdateDatabase.MIGRATION_2_3)
                .addMigrations(UpdateDatabase.MIGRATION_3_4)
                .addMigrations(UpdateDatabase.MIGRATION_4_5).build();
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

    @Provides
    public IUserDAO provideUserDAO(JobLinkDatabase database) {
        return database.userDAO();
    }

    @Provides
    public IMessageDAO provideMessageDAO(JobLinkDatabase database){return database.messageDAO();}

    @Provides
    public INotificationDAO provideNotificationDAO(JobLinkDatabase database){return database.notificationDAO();}
}
