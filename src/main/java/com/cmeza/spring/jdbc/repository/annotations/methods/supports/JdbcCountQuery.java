package com.cmeza.spring.jdbc.repository.annotations.methods.supports;

import com.cmeza.spring.jdbc.repository.annotations.JdbcLoggeable;
import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.naming.impl.NoOpNamingStrategy;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@JdbcLoggeable
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
