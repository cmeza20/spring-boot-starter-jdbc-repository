package com.cmeza.spring.jdbc.repository.support.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Component
public @interface JdbcRepository {
    boolean loggable() default false;

    String catalog() default "";

    String schema() default "";

    String repositoryTemplateBeanName() default "jdbcRepositoryTemplate";

    String dslName() default "";
}
