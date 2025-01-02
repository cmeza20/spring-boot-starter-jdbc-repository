package com.cmeza.spring.jdbc.repository.annotations.methods.operations;

import com.cmeza.spring.jdbc.repository.annotations.JdbcLoggeable;
import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.naming.impl.NoOpNamingStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@JdbcLoggeable
public @interface JdbcCall {
    String value();

    String schema() default "";

    String catalog() default "";

    String[] parameters() default {};

    CallType type() default CallType.CALL;

    @AliasFor(annotation = JdbcLoggeable.class)
    boolean loggeable() default false;

    Class<? extends NamingStrategy> parametersNamingStrategy() default NoOpNamingStrategy.class;

    @Getter
    @AllArgsConstructor
    enum CallType {
        CALL(true), EXEC(false);
        final boolean parenthesis;
    }
}
