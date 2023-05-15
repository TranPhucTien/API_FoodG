package com.nhom7.foodg.exceptions;

public class MissingFieldException extends  RuntimeException{
    public MissingFieldException(String message) {
        super(message);
    }

}