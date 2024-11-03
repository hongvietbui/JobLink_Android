package com.SE1730.Group3.JobLink.src.domain.useCases;

import com.SE1730.Group3.JobLink.src.data.models.all.NotificationDTO;
import com.SE1730.Group3.JobLink.src.data.models.api.ApiResp;
import com.SE1730.Group3.JobLink.src.domain.repositories.IUserRepository;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class GetNotificationUseCase {
    private final IUserRepository userRepository;

    @Inject
    public GetNotificationUseCase(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Observable<ApiResp<List<NotificationDTO>>> execute() throws IOException {
        return userRepository.getUserNotifications();
    }

}
