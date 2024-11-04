package com.SE1730.Group3.JobLink.src.domain.enums;

public enum PaymentType {
    WITHDRAW("Withdraw"),
    DEPOSIT("Deposit");

    public final String value;

    PaymentType(String value) {
        this.value = value;
    }

    public static PaymentType fromInt(int code) {
        switch (code) {
            case 0:
                return WITHDRAW;
            case 1:
                return DEPOSIT;
            default:
                return null;
        }
    }

    public String getDisplayName() {
        return value;
    }
}


