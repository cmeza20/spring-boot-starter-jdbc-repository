package com.cmeza.spring.jdbc.repository.annotations.methods.operations;

import com.cmeza.spring.jdbc.repository.annotations.JdbcLoggeable;
import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.naming.impl.NoOpNamingStrategy;
import org.springframework.core.annotation.AliasFor;
import org.springframework.jdbc.core.RowMapper;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@JdbcLoggeable
public @interface JdbcPagination {
    String schema() default "";

    String catalog() default "";

    String[] columns() default { "*" };

    String table();

    String alias() default "t";

    String where() default "";

    String[] orderBy() default {};

    String[] groupBy() default {};

    Class<? extends RowMapper> mapper() default RowMapper.class;

    @AliasFor(annotation = JdbcLoggeable.class)
    boolean loggeable() default false;

    Class<? extends NamingStrategy> columnsNamingStrategy() default NoOpNamingStrategy.class;
}
