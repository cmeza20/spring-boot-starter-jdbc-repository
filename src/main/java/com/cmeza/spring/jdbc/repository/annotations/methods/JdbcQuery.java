package com.cmeza.spring.jdbc.repository.annotations.methods;

import com.cmeza.spring.jdbc.repository.annotations.JdbcLoggeable;
import com.cmeza.spring.jdbc.repository.mappers.JdbcRowMapper;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@JdbcLoggeable
public @interface JdbcQuery {
    String value();

    Class<? extends JdbcRowMapper> mapper() default JdbcRowMapper.class;

    @AliasFor(annotation = JdbcLoggeable.class)
    boolean loggeable() default false;
}
