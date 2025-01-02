package com.cmeza.spring.jdbc.repository.annotations.methods.operations;

import com.cmeza.spring.jdbc.repository.annotations.JdbcLoggeable;
import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.naming.impl.NoOpNamingStrategy;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@JdbcLoggeable
public @interface JdbcInsert {
    String table();

    String catalog() default "";

    String schema() default "";

    String[] columns() default {};

    String[] generatedKeyColumns() default {};

    boolean accessTableColumnMetaData() default true;

    boolean overrideIncludeSynonymsDefault() default false;

    @AliasFor(annotation = JdbcLoggeable.class)
    boolean loggeable() default false;

    Class<? extends NamingStrategy> columnsNamingStrategy() default NoOpNamingStrategy.class;
}
