package com.cmeza.spring.jdbc.repository.annotations.methods.operations;

import com.cmeza.spring.jdbc.repository.annotations.JdbcLoggeable;
import org.springframework.core.annotation.AliasFor;
import org.springframework.jdbc.core.RowMapper;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@JdbcLoggeable
public @interface JdbcRawPagination {
    String value();

    Class<? extends RowMapper> mapper() default RowMapper.class;

    @AliasFor(annotation = JdbcLoggeable.class)
    boolean loggeable() default false;
}
