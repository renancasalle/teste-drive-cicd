package com.example.api.exception;

import com.example.api.exception.product.ProductFieldsNullException;
import com.example.api.exception.product.ProductNameAlreadyExistsException;
import com.example.api.exception.product.ProductNotFoundException;
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

        return buildExceptionResponse(HttpStatus.BAD_REQUEST, "Validation Error");
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponse> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, "Malformed JSON request");
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse> handleGeneralException(Exception ex) {
        return buildExceptionResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    @ExceptionHandler(ProductNameAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleProductNameAlreadyExists(ProductNameAlreadyExistsException ex) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ExceptionResponse> handleProductNotFound(ProductNotFoundException ex) {
        return buildExceptionResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ProductFieldsNullException.class)
    public ResponseEntity<ExceptionResponse> handleProductFieldsNull(ProductFieldsNullException ex) {
        return buildExceptionResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    private ResponseEntity<ExceptionResponse> buildExceptionResponse(HttpStatus status, String detail) {
        return ResponseEntity
                .status(status)
                .body(new ExceptionResponse(status.value(), detail));
    }
}