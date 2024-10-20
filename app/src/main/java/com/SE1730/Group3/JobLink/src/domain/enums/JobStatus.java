package com.SE1730.Group3.JobLink.src.domain.enums;

public enum JobStatus {
    PendingApproval("pending-approval"),
    Approved("approved"),
    Rejected("rejected"),
    Expired("expired"),
    Deleted("deleted"),
    Completed("completed"),
    InProgress("in-progress");

    public final String value;

    JobStatus(String value) {
        this.value = value;
    }
}
