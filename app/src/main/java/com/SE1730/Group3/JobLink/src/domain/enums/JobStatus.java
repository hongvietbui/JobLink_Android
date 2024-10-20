package com.SE1730.Group3.JobLink.src.domain.enums;

public enum JobStatus {
    PENDING_APPROVAL("pending-approval"),
    APPROVED("approved"),
    REJECTED("rejected"),
    EXPIRED("expired"),
    DELETED("deleted"),
    COMPLETED("completed"),
    IN_PROGRESS("in-progress");

    public final String value;

    JobStatus(String value) {
        this.value = value;
    }

    public static JobStatus fromString(String text) {
        for (JobStatus b : JobStatus.values()) {
            if (b.value.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
