package com.cmeza.spring.jdbc.repository.support.annotations.methods.operations;

import com.cmeza.spring.jdbc.repository.support.annotations.JdbcLoggable;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
@JdbcLoggable
public @interface JdbcCall {
    String value();

    String schema() default "";

    String catalog() default "";

    String[] parameters() default {};

    CallType type() default CallType.CALL;

    @AliasFor(annotation = JdbcLoggable.class)
    boolean loggable() default false;

    Class<? extends NamingStrategy> parametersNamingStrategy() default NoOpNamingStrategy.class;

    @Getter
    @AllArgsConstructor
    enum CallType {
        CALL(true), EXEC(false);
        final boolean parenthesis;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @Documented
    @interface DSL {

    }
}
