package com.cmeza.spring.jdbc.repository.support.annotations;

import com.cmeza.spring.ioc.handler.annotations.EnableIocHandlers;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Inherited
@EnableIocHandlers(JdbcRepository.class)
public @interface EnableJdbcRepositories {

    @AliasFor(annotation = EnableIocHandlers.class)
    String[] basePackages() default {};
}
