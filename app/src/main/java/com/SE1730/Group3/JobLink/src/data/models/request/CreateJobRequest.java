package com.SE1730.Group3.JobLink.src.data.models.request;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CreateJobRequest {
    private String name;
    private String description;
    private int duration;
    private double price;
    private String avatar;
    private String startTime; // Đổi thành String
    private String endTime;   // Đổi thành String
}
