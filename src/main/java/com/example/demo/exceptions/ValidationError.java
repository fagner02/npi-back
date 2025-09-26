package com.example.demo.exceptions;

import org.springframework.validation.BindingResult;

import java.util.List;

public class ValidationError extends RuntimeException {
    public List<String> errors;

    public ValidationError(BindingResult result) {
        errors = result.getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .toList();
    }
}
