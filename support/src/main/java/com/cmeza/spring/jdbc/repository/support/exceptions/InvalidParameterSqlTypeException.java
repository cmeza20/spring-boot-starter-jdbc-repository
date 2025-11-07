package com.cmeza.spring.jdbc.repository.support.exceptions;

public class InvalidParameterSqlTypeException extends RuntimeException {
    private static final long serialVersionUID = -6965982890091007447L;

    public InvalidParameterSqlTypeException(String message) {
        super(message);
    }
}
