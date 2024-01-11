package com.mjc.school.service.error.exceptions;

public class NotFoundRuntimeException extends RuntimeException {
    public NotFoundRuntimeException(String message) {
        super("ERROR 404 Resource not found: " + message);
    }
}
