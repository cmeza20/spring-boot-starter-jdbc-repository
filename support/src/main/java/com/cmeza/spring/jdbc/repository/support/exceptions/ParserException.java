package com.cmeza.spring.jdbc.repository.support.exceptions;

public class ParserException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public ParserException(String message) {
        super(message);
    }

    public ParserException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParserException(Throwable cause) {
        super(cause);
    }
}
