package com.nhom7.foodg.utils;

public class VariableHandler<T> {
    public static <T> boolean isNullOrEmpty(T value) {
        if (value instanceof String string) {
            return string.trim().equals("") || string.isEmpty();
        } else {
            return value == null;
        }
    }
}
