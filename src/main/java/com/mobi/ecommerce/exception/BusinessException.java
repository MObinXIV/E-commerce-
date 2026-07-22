package com.mobi.ecommerce.exception;

import org.springframework.http.HttpStatus;

public class BusinessException extends RuntimeException{

    private final HttpStatus httpStatus;

    // Basic constructor with default BAD_REQUEST status
    public BusinessException(String message) {
        this(message, HttpStatus.BAD_REQUEST);
    }

    public BusinessException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
