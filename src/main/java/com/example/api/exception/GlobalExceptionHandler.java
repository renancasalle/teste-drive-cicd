package com.example.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    public ResponseEntity<ExceptionResponse> handlerMethodArguementNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });

        return buildExceptionResponse(null, 400, "Validation Error");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return buildExceptionResponse(null, 400, "Malformed JSON request");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGeneralException(Exception ex) {
        return buildExceptionResponse(null, 500, "Internal Server Error");
    }

    @ExceptionHandler(ProductNameAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleProductNameAlreadyExists(ProductNameAlreadyExistsException ex) {
        return buildExceptionResponse(null, 400, ex.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleProductNotFound(ProductNotFoundException ex) {
        return buildExceptionResponse(null, 404, ex.getMessage());
    }

    @ExceptionHandler(ProductFieldsNullException.class)
    public ResponseEntity<ExceptionResponse> handleProductFieldsNull(ProductFieldsNullException ex) {
        return buildExceptionResponse(null, 400, ex.getMessage());
    }

    private ResponseEntity<ExceptionResponse> buildExceptionResponse(HttpStatus staus, String detail) {
        return ResponseEntity
                .status(status)
                .body(new ExceptionResponse(status.value(), detail));
    }

}
