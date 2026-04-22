// package com.keepNotes.demo.controller;

// public class GlobalExceptionHandler {
    
// }


package com.keepNotes.demo.controller;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ResponseErrorException> handleResponseStatusException(
            ResponseStatusException ex,
            HttpServletRequest request) {

        HttpStatusCode statusCode = ex.getStatusCode();
        String message = ex.getReason() != null ? ex.getReason() : "Request failed";

        ResponseErrorException error = new ResponseErrorException(
                LocalDateTime.now(),
                statusCode.value(),
                message,
                request.getRequestURI());

        return ResponseEntity.status(statusCode).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseErrorException> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        String message = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ResponseErrorException error = new ResponseErrorException(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                message.isEmpty() ? "Validation failed" : message,
                request.getRequestURI());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ResponseErrorException> handleBadJson(
            HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        ResponseErrorException error = new ResponseErrorException(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Malformed JSON request",
                request.getRequestURI());

        return ResponseEntity.badRequest().body(error);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseErrorException> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        ResponseErrorException error = new ResponseErrorException(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal server error",
                request.getRequestURI());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}