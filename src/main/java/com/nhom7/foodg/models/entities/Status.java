package com.nhom7.foodg.models.entities;

public enum Status {
    PENDING(1),
    ACTIVE(2),
    REMOVE(3);
    private final int value;

    private Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
