package com.SE1730.Group3.JobLink.src.data.models.all;

import java.util.UUID;

import lombok.Data;

@Data
public class JobWorkerDTO {
    private UUID jobId;
    private UUID workerId;
    private String applyStatus;
}
