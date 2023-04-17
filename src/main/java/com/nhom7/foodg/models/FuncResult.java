package com.nhom7.foodg.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(staticName = "create")
public class FuncResult<T> {
    private HttpStatus status;
    private String message;
    public T data;
}
