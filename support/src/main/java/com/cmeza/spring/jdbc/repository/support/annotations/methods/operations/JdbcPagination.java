package com.cmeza.spring.jdbc.repository.support.annotations.methods.operations;

import com.cmeza.spring.jdbc.repository.support.annotations.JdbcLoggable;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import org.springframework.core.annotation.AliasFor;
import org.springframework.jdbc.core.RowMapper;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@JdbcLoggable
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

    @AliasFor(annotation = JdbcLoggable.class)
    boolean loggable() default false;

    Class<? extends NamingStrategy> columnsNamingStrategy() default NoOpNamingStrategy.class;

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @Documented
    @interface DSL {

    }
}
