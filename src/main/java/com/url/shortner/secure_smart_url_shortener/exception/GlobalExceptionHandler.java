package com.url.shortner.secure_smart_url_shortener.exception;

import org.apache.coyote.BadRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", Instant.now());
        body.put("status", ex.getStatusCode().value());
        body.put("error", ex.getReason());
        return new ResponseEntity<>(body, ex.getStatusCode());
    }

    @ExceptionHandler({IllegalArgumentException.class, NullPointerException.class})
    public ResponseEntity<ErrorResponse<?>> handleBadRequest(Exception ex) {
        ErrorResponse er = new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponse<?>> handleInvalidCredentials(InvalidCredentialsException ex) {
        ErrorResponse er = new ErrorResponse(HttpStatus.FORBIDDEN.toString(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(er);
    }

    @ExceptionHandler(UnAuthorizedException.class)
    public ResponseEntity<ErrorResponse<?>> handleInvalidCredentials(UnAuthorizedException ex) {
        ErrorResponse er = new ErrorResponse(HttpStatus.UNAUTHORIZED.toString(), ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(er);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse<?>> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {

        String message = "Invalid request payload";

        Throwable cause = ex.getMostSpecificCause();

        if (cause instanceof IllegalArgumentException) {
            String causeMessage = cause.getMessage();
            log.info("Cause message {}", causeMessage);
            if (causeMessage.startsWith("Role")) {
                message = "Invalid role provided. Only ADMIN, USER are allowed!";
            } else if (causeMessage.startsWith("AccessType")) {
                message = "Invalid access type provided. Only PUBLIC, PRIVATE, ROLE_BASED are allowed!";
            } else {
                message = causeMessage;
            }
        }

        ErrorResponse er = new ErrorResponse(HttpStatus.BAD_REQUEST.toString(), message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        String msg = ex.getBindingResult().getFieldError().getDefaultMessage();
        ErrorResponse er = new ErrorResponse(ex.getStatusCode().toString(), msg);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(er);
    }
}
