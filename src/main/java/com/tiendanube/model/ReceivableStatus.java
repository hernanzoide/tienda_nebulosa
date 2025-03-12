package com.tiendanube.model;

import lombok.Getter;

@Getter
public enum ReceivableStatus {

    PAID("paid"),
    WAITING_FUNDS("waiting_funds");

    private final String name;

    ReceivableStatus(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static ReceivableStatus fromValue(String value) {
        for (ReceivableStatus status : ReceivableStatus.values()) {
            if (status.getName().equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + value);
    }
}
