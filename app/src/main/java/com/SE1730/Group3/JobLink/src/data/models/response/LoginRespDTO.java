package com.SE1730.Group3.JobLink.src.data.models.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginRespDTO {
    private String accessToken;
    private String refreshToken;
}
