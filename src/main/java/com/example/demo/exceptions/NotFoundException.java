package com.example.demo.exceptions;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String entity) {
        super("A entidade " + entity + " não foi encontrada");
    }
}