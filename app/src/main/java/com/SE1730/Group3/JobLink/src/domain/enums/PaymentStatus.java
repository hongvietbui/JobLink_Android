package com.SE1730.Group3.JobLink.src.domain.enums;

public enum PaymentStatus {
    PENDING("Pending"),
    DONE("Done"),
    REJECTED("Rejected");

    public final String value;

    PaymentStatus(String value) {
        this.value = value;
    }

    public static PaymentStatus fromInt(int code) {
        switch (code) {
            case 0:
                return PENDING;
            case 1:
                return DONE;
            case 2:
                return REJECTED;
            default:
                return null;
        }
    }

    public String getDisplayName() {
        return value;
    }
}

