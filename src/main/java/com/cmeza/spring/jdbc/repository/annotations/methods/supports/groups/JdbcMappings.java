package com.cmeza.spring.jdbc.repository.annotations.methods.supports.groups;

import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcMapping;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface JdbcMappings {
    JdbcMapping[] value();
}
