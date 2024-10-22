package com.SE1730.Group3.JobLink.src.data.models.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RefreshTokenReqDTO {
    private String refreshToken;
}
