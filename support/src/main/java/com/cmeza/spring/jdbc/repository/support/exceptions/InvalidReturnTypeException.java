package com.cmeza.spring.jdbc.repository.support.exceptions;

public class InvalidReturnTypeException extends RuntimeException {
    private static final long serialVersionUID = -4303713587841665008L;

    public InvalidReturnTypeException(String message) {
        super(message);
    }
}
