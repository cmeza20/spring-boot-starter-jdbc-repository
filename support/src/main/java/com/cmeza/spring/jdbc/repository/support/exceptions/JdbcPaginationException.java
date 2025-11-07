package com.cmeza.spring.jdbc.repository.support.exceptions;

public class JdbcPaginationException extends RuntimeException {
    private static final long serialVersionUID = -4320005657875846068L;

    public JdbcPaginationException(String message) {
        super(message);
    }

    public JdbcPaginationException(String message, Throwable cause) {
        super(message, cause);
    }
}
