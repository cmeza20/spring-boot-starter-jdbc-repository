package com.cmeza.spring.jdbc.repository.support.annotations.parameters;

import java.lang.annotation.*;
import java.sql.Types;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
@Documented
public @interface JdbcParam {
    String value();

    int type() default Types.NULL;
}
