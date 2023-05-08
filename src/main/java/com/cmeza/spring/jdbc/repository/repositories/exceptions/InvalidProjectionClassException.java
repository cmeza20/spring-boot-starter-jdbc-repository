package com.cmeza.spring.jdbc.repository.repositories.exceptions;

public class InvalidProjectionClassException extends RuntimeException {
    private static final long serialVersionUID = -7671838839791751604L;

    public InvalidProjectionClassException(String message) {
        super(message);
    }
}
