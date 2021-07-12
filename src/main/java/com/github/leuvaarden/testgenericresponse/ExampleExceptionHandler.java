package com.github.leuvaarden.testgenericresponse;

import com.github.leuvaarden.testgenericresponse.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.constraints.NotNull;

@ControllerAdvice
public class ExampleExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ErrorResponse<Object>> handleException(@NotNull Exception e) {
        ErrorResponse.ErrorHolder error = new ErrorResponse.ErrorHolder("Code", e.getMessage(), "Message");
        return new ResponseEntity<>(new ErrorResponse<>(error), HttpStatus.OK);
    }
}
