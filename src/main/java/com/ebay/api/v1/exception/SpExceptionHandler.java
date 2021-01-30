package com.ebay.api.v1.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class SpExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpExceptionHandler.class);


    @ExceptionHandler(SpException.class)
    @ResponseBody
    public ResponseEntity<String> SpException(SpException e) {

        LOGGER.error("Shipping program Exception: {}", e.getMessage());

        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());

    }

}
