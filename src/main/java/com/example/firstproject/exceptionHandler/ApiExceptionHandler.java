package com.example.firstproject.exceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(ErrorMessage.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public ErrorMessage handleAllException(ErrorMessage ex, WebRequest request) {
        return new ErrorMessage(ex.getCode(), ex.getMessage());
    }
}
