package com.SE1730.Group3.JobLink.src.data.models.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ResetPassDTO {
    private String Email;
    private String Password;;
}
