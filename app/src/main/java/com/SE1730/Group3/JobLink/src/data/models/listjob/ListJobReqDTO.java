package com.SE1730.Group3.JobLink.src.data.models.listjob;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ListJobReqDTO {
    private String name;
    private String description;
    private String ownerId;
    private String workerId;
    private String address;
    private int lat;
    private int lon;
    private int status;
}
