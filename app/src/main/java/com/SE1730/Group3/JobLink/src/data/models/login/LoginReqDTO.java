package com.SE1730.Group3.JobLink.src.data.models.login;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginReqDTO {
    private String username;
    private String password;

}
