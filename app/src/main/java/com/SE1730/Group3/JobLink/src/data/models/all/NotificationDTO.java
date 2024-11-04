package com.SE1730.Group3.JobLink.src.data.models.all;

import java.util.Date;
import java.util.UUID;

import dagger.Component;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDTO {
    private UUID id;
    private String message;
    private String timestamp;
    private boolean isRead;
}