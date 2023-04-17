package com.nhom7.foodg.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ModifyException extends RuntimeException
{
    public ModifyException(String message) {
        super(message);
    }
}
