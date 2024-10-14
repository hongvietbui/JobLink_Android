package com.SE1730.Group3.JobLink.src.domain.enums;

public enum UserStatus {
    ACTIVE("active"),
    PENDING_VERIFICATION("pending-verification"),
    SUSPENDED("suspended"),
    LOCKED("locked");


    public final String value;

    UserStatus(String value) {
        this.value = value;
    }
}
