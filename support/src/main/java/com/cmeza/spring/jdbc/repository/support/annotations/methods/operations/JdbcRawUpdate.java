package com.cmeza.spring.jdbc.repository.support.annotations.methods.operations;

import com.cmeza.spring.jdbc.repository.support.annotations.JdbcLoggable;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@JdbcLoggable
public @interface JdbcRawUpdate {
    String value();

    String[] keyColumnNames() default {};

    @AliasFor(annotation = JdbcLoggable.class)
    boolean loggable() default false;

    Class<? extends NamingStrategy> columnsNamingStrategy() default NoOpNamingStrategy.class;

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @Documented
    @interface DSL {

    }
}
