package com.tiendanube.model;

import lombok.Getter;

@Getter
public enum PaymentMethod {

    DEBIT_CARD("debit_card"),
    CREDIT_CARD("credit_card");

    private final String name;

    PaymentMethod(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

    public static PaymentMethod fromValue(String value) {
        for (PaymentMethod method : PaymentMethod.values()) {
            if (method.getName().equals(value)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Unexpected value: " + value);
    }
}
