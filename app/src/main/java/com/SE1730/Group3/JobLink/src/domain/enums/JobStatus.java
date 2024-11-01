package com.SE1730.Group3.JobLink.src.domain.enums;

public enum JobStatus {
    PENDING_APPROVAL("PendingApproval"),
    APPROVED("Approved"),
    REJECTED("Rejected"),
    EXPIRED("Expired"),
    DELETED("Deleted"),
    COMPLETED("Completed"),
    IN_PROGRESS("InProgress"),
    WAITING_FOR_APPLICANTS("WaitingForApplicants");

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
