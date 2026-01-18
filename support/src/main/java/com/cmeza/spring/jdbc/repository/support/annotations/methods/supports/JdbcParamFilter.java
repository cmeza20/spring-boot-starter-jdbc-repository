package com.cmeza.spring.jdbc.repository.support.annotations.methods.supports;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface JdbcParamFilter {
    String[] value();
}
