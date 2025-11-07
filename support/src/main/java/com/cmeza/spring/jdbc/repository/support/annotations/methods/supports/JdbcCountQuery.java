package com.cmeza.spring.jdbc.repository.support.annotations.methods.supports;

import com.cmeza.spring.jdbc.repository.support.annotations.JdbcLoggable;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@JdbcLoggable
public @interface JdbcCountQuery {
    String schema() default "";

    String catalog() default "";

    String[] columns() default {};

    String table();

    String alias() default "t";

    String where() default "";

    String[] groupBy() default {};

    Class<? extends NamingStrategy> columnsNamingStrategy() default NoOpNamingStrategy.class;
}
