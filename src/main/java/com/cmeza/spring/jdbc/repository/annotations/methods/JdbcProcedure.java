package com.cmeza.spring.jdbc.repository.annotations.methods;

import com.cmeza.spring.jdbc.repository.annotations.JdbcLoggeable;
import com.cmeza.spring.jdbc.repository.annotations.parameters.OutParameter;
import com.cmeza.spring.jdbc.repository.mappers.JdbcRowMapper;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@JdbcLoggeable
public @interface JdbcProcedure {
    String name();

    String schema() default "";

    String catalog() default "";

    String[] inParameterNames() default {};

    OutParameter[] outParameters() default {};

    String rowMapperParameterName() default "result";

    Class<? extends JdbcRowMapper> mapper() default JdbcRowMapper.class;

    boolean accessCallParameterMetaData() default false;

    @AliasFor(annotation = JdbcLoggeable.class)
    boolean loggeable() default false;
}
