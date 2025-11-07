package com.cmeza.spring.jdbc.repository.support.annotations.methods.operations;

import com.cmeza.spring.jdbc.repository.support.annotations.JdbcLoggable;
import com.cmeza.spring.jdbc.repository.support.annotations.parameters.Parameter;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import org.springframework.core.annotation.AliasFor;
import org.springframework.jdbc.core.RowMapper;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@JdbcLoggable
public @interface JdbcProcedure {
    String name();

    String schema() default "";

    String catalog() default "";

    String[] inParameterNames() default {};

    Parameter[] outParameters() default {};

    String rowMapperParameterName() default "result";

    Class<? extends RowMapper> mapper() default RowMapper.class;

    boolean accessCallParameterMetaData() default false;

    @AliasFor(annotation = JdbcLoggable.class)
    boolean loggable() default false;

    Class<? extends NamingStrategy> parametersNamingStrategy() default NoOpNamingStrategy.class;

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @Documented
    @interface DSL {

    }
}
