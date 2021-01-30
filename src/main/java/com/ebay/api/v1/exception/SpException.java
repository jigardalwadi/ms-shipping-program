package com.ebay.api.v1.exception;

import org.springframework.http.HttpStatus;

public class SpException extends RuntimeException{
    private HttpStatus httpStatus;

    public SpException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public SpException(String message) {
        super(message);
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }


}
