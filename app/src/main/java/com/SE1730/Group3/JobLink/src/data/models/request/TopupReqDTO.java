package com.SE1730.Group3.JobLink.src.data.models.request;

import java.util.Date;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TopupReqDTO {
    private Date fromDate;
    private Date toDate;
}
