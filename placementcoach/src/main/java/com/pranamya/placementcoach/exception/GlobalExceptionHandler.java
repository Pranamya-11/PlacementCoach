package com.pranamya.placementcoach.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException exception)
    {
        ErrorResponse errorResponse=new ErrorResponse( false,
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(errorResponse);
    }

    @ExceptionHandler(DuplicateApplicationException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateApplication(DuplicateApplicationException exception){
        ErrorResponse errorResponse=new ErrorResponse( false,
                exception.getMessage(),
                HttpStatus.CONFLICT.value(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(errorResponse);
    }

}
