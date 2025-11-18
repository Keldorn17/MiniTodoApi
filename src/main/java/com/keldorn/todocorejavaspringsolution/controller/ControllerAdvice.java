package com.keldorn.todocorejavaspringsolution.controller;

import com.keldorn.todocorejavaspringsolution.dto.error.ErrorResponse;
import com.keldorn.todocorejavaspringsolution.exception.TodoNotFoundException;
import com.keldorn.todocorejavaspringsolution.exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@RestControllerAdvice
public class ControllerAdvice {

    private final String CLIENT_ERROR = "ClientError";
    private final String INTERNAL_ERROR = "InternalError";

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

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Object> handleException(UsernameNotFoundException exception) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErrorResponse errorResponse = ErrorResponse.builder()
                .type(CLIENT_ERROR)
                .title(status.getReasonPhrase())
                .statusCode(status)
                .details(exception.getMessage())
                .build();
        return buildResponse(errorResponse);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleException(AccessDeniedException exception) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        ErrorResponse errorResponse = ErrorResponse.builder()
                .type(CLIENT_ERROR)
                .title(status.getReasonPhrase())
                .statusCode(status)
                .details(exception.getMessage())
                .build();
        return buildResponse(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exception) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse = ErrorResponse.builder()
                .type(INTERNAL_ERROR)
                .title(status.getReasonPhrase())
                .statusCode(status)
                .details(exception.getMessage())
                .build();
        return buildResponse(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleException(MethodArgumentNotValidException exception) {
        List<String> errors = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();

        String errorMessage = String.join("; ", errors);

        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErrorResponse errorResponse = ErrorResponse.builder()
                .type(CLIENT_ERROR)
                .title(status.getReasonPhrase())
                .statusCode(status)
                .details(errorMessage)
                .build();

        return buildResponse(errorResponse);
    }

    private ResponseEntity<Object> buildResponse(ErrorResponse errorResponse) {
        return ResponseEntity.status(errorResponse.getStatusCode()).body(errorResponse);
    }
}
