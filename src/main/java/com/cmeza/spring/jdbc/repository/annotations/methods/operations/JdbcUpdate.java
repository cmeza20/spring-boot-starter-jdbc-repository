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
public @interface JdbcUpdate {
    String schema() default "";

    String catalog() default "";

    String[] updateSets();

    String table();

    String alias() default "";

    String where() default "";

    String[] keyColumnNames() default {};

    @AliasFor(annotation = JdbcLoggeable.class)
    boolean loggeable() default false;

    Class<? extends NamingStrategy> columnsNamingStrategy() default NoOpNamingStrategy.class;
}
