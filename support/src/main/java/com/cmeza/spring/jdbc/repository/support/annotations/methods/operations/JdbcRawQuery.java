package com.cmeza.spring.jdbc.repository.support.annotations.methods.operations;

import com.cmeza.spring.jdbc.repository.support.annotations.JdbcLoggable;
import org.springframework.core.annotation.AliasFor;
import org.springframework.jdbc.core.RowMapper;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@JdbcLoggable
public @interface JdbcRawQuery {
    String value();

    Class<? extends RowMapper> mapper() default RowMapper.class;

    @AliasFor(annotation = JdbcLoggable.class) boolean loggable() default false;

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @Documented
    @interface DSL {

    }
}
