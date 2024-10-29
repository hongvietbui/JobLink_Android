package com.SE1730.Group3.JobLink.src.presentation.DI;

import com.microsoft.signalr.HubConnection;
import com.microsoft.signalr.HubConnectionBuilder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class SignalRModule {
    @Provides
    @Singleton
    HubConnection provideTransferHubConnection() {
        return HubConnectionBuilder.create("http://10.0.2.2:8080/hub/transfer").build();
    }
}