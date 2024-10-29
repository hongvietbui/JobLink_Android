package com.SE1730.Group3.JobLink.src.presentation.DI;

import com.microsoft.signalr.HubConnection;

import dagger.hilt.EntryPoint;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@EntryPoint
@InstallIn(SingletonComponent.class)
public interface JobLinkEntryPoint {
    HubConnection getTransferHubConnection();
}
