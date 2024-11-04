package com.SE1730.Group3.JobLink.src.domain.useCases;

import androidx.lifecycle.LiveData;

import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.dao.INotificationDAO;
import com.SE1730.Group3.JobLink.src.domain.entities.Notification;
import com.SE1730.Group3.JobLink.src.domain.repositories.IUserRepository;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class GetNotificationUseCase {
    private final INotificationDAO notificationDAO;

    @Inject
    public GetNotificationUseCase(INotificationDAO notificationDAO) {
        this.notificationDAO = notificationDAO;
    }

    public LiveData<List<Notification>> execute() throws IOException {
        return notificationDAO.getAllNotifications();
    }

}
