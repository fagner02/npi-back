package com.example.demo.dto;

import java.util.List;

public class ErrorResponse {
    public String message;
    public List<String> errors;

    public ErrorResponse(String message, List<String> errors) {
        this.message = message;
        this.errors = errors;
    }
}
