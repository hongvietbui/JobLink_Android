package com.SE1730.Group3.JobLink.src.data.models.response;

import lombok.Data;

@Data
public class LoginRespDTO {
    private String accessToken;
    private String refreshToken;
}
