package com.cmeza.spring.jdbc.repository.annotations.methods.supports;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface JdbcFromTable {

    String catalog() default "";

    String schema() default "";

    String table();

    String alias();
}
