package com.cmeza.spring.jdbc.repository.annotations.parameters;

import java.lang.annotation.*;
import java.sql.Types;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Documented
public @interface JdbcParam {
    String value();

    int type() default Types.NULL;
}
