package com.cmeza.spring.jdbc.repository.support.annotations.parameters;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE_USE})
@Documented
public @interface Parameter {
    String value();

    int type();

    int order() default 0;
}
