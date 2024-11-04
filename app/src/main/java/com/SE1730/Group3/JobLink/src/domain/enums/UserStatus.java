package com.SE1730.Group3.JobLink.src.domain.enums;

public enum UserStatus {
    ACTIVE("Active"),
    PENDING_VERIFICATION("PendingVerification"),
    SUSPENDED("Suspended"),
    LOCKED("Locked");

    public final String value;

    UserStatus(String value) {
        this.value = value;
    }

    public static UserStatus fromString(String text) {
        for (UserStatus b : UserStatus.values()) {
            if (b.value.equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
    public String getDisplayName() {
        return value;
    }
}
