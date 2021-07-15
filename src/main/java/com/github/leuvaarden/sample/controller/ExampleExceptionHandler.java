package com.github.leuvaarden.sample.controller;

import com.github.leuvaarden.sample.dto.ErrorHolder;
import com.github.leuvaarden.sample.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.constraints.NotNull;

@ControllerAdvice
public class ExampleExceptionHandler {
    private static final Logger logger = LoggerFactory.getLogger(ExampleExceptionHandler.class);

    @ExceptionHandler
    public ResponseEntity<ErrorResponse<Object>> handleException(@NotNull Exception e) {
        logger.error("Caught", e);
        ErrorHolder errorHolder = new ErrorHolder("500", e.getMessage(), "Internal server error");
        logger.info("Returning error: [{}]", errorHolder);
        return new ResponseEntity<>(new ErrorResponse<>(errorHolder), HttpStatus.OK);
    }
}
