package com.SE1730.Group3.JobLink.src.data.models.all;

import lombok.Data;

@Data
public class UserHompageDTO {
    private String userName;
    private String accountBalance ;
    private int totalJobDone ;
    private String amountEarnedToday ;
    private String amountEarnedThisMonth ;
    private String depositAmount ;
    private int createJobThisMonth;
}
