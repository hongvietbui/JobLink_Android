package com.SE1730.Group3.JobLink.src.data.models.all;

import com.SE1730.Group3.JobLink.src.domain.enums.JobStatus;

import java.util.UUID;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CreateJobDTO {
    private String name;
    private String description;
    private JobStatus status;
    private Double duration ;
    private Double price;
}
