package com.SE1730.Group3.JobLink.src.data.models.request;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChangePassReqDTO {
    private UUID userId;
    private String currentPassword;
    private String newPassword;
}
