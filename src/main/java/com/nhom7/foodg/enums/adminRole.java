package com.nhom7.foodg.enums;

public enum adminRole {
    SUPPER_ADMIN("supperAdmin"),
    ADMIN("admin");

    private final String description;

    adminRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
