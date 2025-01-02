package com.cmeza.spring.jdbc.repository.annotations.methods.supports;

import com.cmeza.spring.jdbc.repository.annotations.JdbcLoggeable;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@JdbcLoggeable
public @interface JdbcRawCountQuery {
    String value();
}
