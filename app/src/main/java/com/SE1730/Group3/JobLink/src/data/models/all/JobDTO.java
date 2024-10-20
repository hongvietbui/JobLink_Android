package com.SE1730.Group3.JobLink.src.data.models.all;

import com.SE1730.Group3.JobLink.src.domain.enums.JobStatus;

import java.util.UUID;

import lombok.Data;

@Data
public class JobDTO {
    private UUID id;
    private String name;
    private String description;
    private UUID ownerId;
    private UUID workerId;
    private String address;
    private int lat;
    private int lon;
    private JobStatus status;
}
