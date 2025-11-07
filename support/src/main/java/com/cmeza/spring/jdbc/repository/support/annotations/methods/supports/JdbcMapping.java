package com.cmeza.spring.jdbc.repository.support.annotations.methods.supports;

import com.cmeza.spring.jdbc.repository.support.annotations.methods.supports.groups.JdbcMappings;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Repeatable(JdbcMappings.class)
@Documented
public @interface JdbcMapping {
    String to();

    String from() default "";

    int type();
}
