package com.cmeza.spring.jdbc.repository.support.annotations.methods.supports;

import com.cmeza.spring.jdbc.repository.support.annotations.JdbcLoggable;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@JdbcLoggable
public @interface JdbcRawCountQuery {
    String value();
}
