package com.nhom7.foodg.enums;

public enum gender {
    FEMALE("female"),
    MALE("male");

    private final String description;

    gender(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
