package com.cmeza.spring.jdbc.repository.support.exceptions;

public class JdbcException extends RuntimeException {

    private static final long serialVersionUID = -8039095575288153349L;

    public JdbcException(String message) {
        super(message);
    }

    public JdbcException(String message, Throwable cause) {
        super(message, cause);
    }

    public JdbcException(Throwable cause) {
        super(cause);
    }
}
