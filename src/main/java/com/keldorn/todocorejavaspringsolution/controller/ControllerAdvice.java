package com.keldorn.todocorejavaspringsolution.controller;

import com.keldorn.todocorejavaspringsolution.dto.error.ErrorResponse;
import com.keldorn.todocorejavaspringsolution.exception.EmailIsTakenException;
import com.keldorn.todocorejavaspringsolution.exception.TodoNotFoundException;
import com.keldorn.todocorejavaspringsolution.exception.UserNotFoundException;
import com.keldorn.todocorejavaspringsolution.exception.UsernameIsTakenException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.nio.file.AccessDeniedException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    private final String CLIENT_ERROR = "ClientError";
    private final String INTERNAL_ERROR = "InternalError";

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<Object> handleException(TodoNotFoundException exception) {
        log.warn("TodoNotFoundException: {}", exception.getMessage());
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
        log.warn("UserNotFoundException: {}", exception.getMessage());
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
        log.warn("SQLIntegrityConstraintViolationException: {}", exception.getMessage());
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
        log.warn("UsernameNotFoundException: {}", exception.getMessage());
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
        log.warn("AccessDeniedException: {}", exception.getMessage());
        HttpStatus status = HttpStatus.FORBIDDEN;
        ErrorResponse errorResponse = ErrorResponse.builder()
                .type(CLIENT_ERROR)
                .title(status.getReasonPhrase())
                .statusCode(status)
                .details(exception.getMessage())
                .build();
        return buildResponse(errorResponse);
    }

    @ExceptionHandler(UsernameIsTakenException.class)
    public ResponseEntity<Object> handleException(UsernameIsTakenException exception) {
        log.warn("UsernameIsTakenException: {}", exception.getMessage());
        HttpStatus status = HttpStatus.CONFLICT;
        ErrorResponse errorResponse = ErrorResponse.builder()
                .type(CLIENT_ERROR)
                .title(status.getReasonPhrase())
                .statusCode(status)
                .details(exception.getMessage())
                .build();
        return buildResponse(errorResponse);
    }

    @ExceptionHandler(EmailIsTakenException.class)
    public ResponseEntity<Object> handleException(EmailIsTakenException exception) {
        log.warn("EmailIsTakenException: {}", exception.getMessage());
        HttpStatus status = HttpStatus.CONFLICT;
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
        log.warn("Exception: {}", exception.getMessage());
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
        log.warn("MethodArgumentNotValidException: {}", exception.getMessage());
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
