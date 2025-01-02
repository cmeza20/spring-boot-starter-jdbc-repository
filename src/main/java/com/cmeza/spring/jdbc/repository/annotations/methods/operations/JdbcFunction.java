package com.cmeza.spring.jdbc.repository.annotations.methods.operations;

import com.cmeza.spring.jdbc.repository.annotations.JdbcLoggeable;
import com.cmeza.spring.jdbc.repository.annotations.parameters.Parameter;
import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.naming.impl.NoOpNamingStrategy;
import org.springframework.core.annotation.AliasFor;
import org.springframework.jdbc.core.RowMapper;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@JdbcLoggeable
public @interface JdbcFunction {
    String name();

    String schema() default "";

    String catalog() default "";

    String[] inParameterNames() default {};

    Parameter[] outParameters() default {};

    String rowMapperParameterName() default "result";

    Class<? extends RowMapper> mapper() default RowMapper.class;

    boolean accessCallParameterMetaData() default false;

    @AliasFor(annotation = JdbcLoggeable.class)
    boolean loggeable() default false;

    Class<? extends NamingStrategy> parametersNamingStrategy() default NoOpNamingStrategy.class;

}
