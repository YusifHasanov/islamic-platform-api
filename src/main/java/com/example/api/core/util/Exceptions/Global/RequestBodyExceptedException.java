package com.example.api.core.util.Exceptions.Global;

public class RequestBodyExceptedException extends RuntimeException {
    public RequestBodyExceptedException(String message) {
        super(message);
    }
}
