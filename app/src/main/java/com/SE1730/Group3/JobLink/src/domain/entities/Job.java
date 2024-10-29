package com.SE1730.Group3.JobLink.src.domain.entities;

import com.SE1730.Group3.JobLink.src.domain.entities.baseEntities.BaseEntity;
import com.SE1730.Group3.JobLink.src.domain.enums.JobStatus;

import java.util.UUID;

import lombok.Data;

@Data
public class Job extends BaseEntity<UUID> {
    private String name;
    private String Description;
    private String OwnerId;
    private String Address;
    private int Lat;
    private int Lon;
    private JobStatus Status;
    private User Owner;
    private User Worker;
}
