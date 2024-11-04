package com.SE1730.Group3.JobLink.src.data.models.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatDTOReq {
    public String senderId;
    public String receiverId;
    public String message;
    public String jobId;
    public Boolean isWorker;
}
