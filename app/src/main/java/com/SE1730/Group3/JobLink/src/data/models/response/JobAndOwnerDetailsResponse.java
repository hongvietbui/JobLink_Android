package com.SE1730.Group3.JobLink.src.data.models.response;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;
@Builder
@Data
public class JobAndOwnerDetailsResponse {
    private UUID jobId;
    private  String jobName;
    private String description;
    private String avatar;
    private int lat;
    private int lon;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
}
