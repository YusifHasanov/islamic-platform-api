package com.example.api.core.util.Exceptions.Global;

public class NotFoundException extends RuntimeException{
    public NotFoundException(String message) {
        super(message);
    }
}
