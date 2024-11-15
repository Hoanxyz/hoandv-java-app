package com.example.firstproject.exceptionHandler;

import lombok.Data;

@Data
public class ErrorMessage extends RuntimeException {
    private int code;
    private String msg;

    public ErrorMessage(int code, String msg) {
        super(msg);
        this.code = code;
    }
}
