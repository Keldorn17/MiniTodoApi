package com.keldorn.todocorejavaspringsolution.controller;

import com.keldorn.todocorejavaspringsolution.exception.TodoNotFoundException;
import com.keldorn.todocorejavaspringsolution.exception.UserNotFoundException;
import com.keldorn.todocorejavaspringsolution.dto.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
public class ControllerAdvice {

    private final String CLIENT_ERROR = "ClientError";

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<Object> handleException(TodoNotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse errorResponse = ErrorResponse.builder()
                .type(CLIENT_ERROR)
                .title(status.getReasonPhrase())
                .statusCode(status)
                .details(exception.getMessage())
                .build();
        return buildResponse(errorResponse);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleException(UserNotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse errorResponse = ErrorResponse.builder()
                .type(CLIENT_ERROR)
                .title(status.getReasonPhrase())
                .statusCode(status)
                .details(exception.getMessage())
                .build();
        return buildResponse(errorResponse);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public ResponseEntity<Object> handleException(SQLIntegrityConstraintViolationException exception) {
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorResponse errorResponse = ErrorResponse.builder()
                .type(CLIENT_ERROR)
                .title(status.getReasonPhrase())
                .statusCode(status)
                .details(exception.getMessage())
                .build();
        return buildResponse(errorResponse);
    }

    private ResponseEntity<Object> buildResponse(ErrorResponse errorResponse) {
        return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
    }
}
