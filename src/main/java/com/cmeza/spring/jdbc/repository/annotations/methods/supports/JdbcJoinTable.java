package com.cmeza.spring.jdbc.repository.annotations.methods.supports;

import com.cmeza.spring.jdbc.repository.annotations.methods.supports.groups.JdbcJoinTables;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Repeatable(JdbcJoinTables.class)
@Documented
public @interface JdbcJoinTable {

    String catalog() default "";

    String schema() default "";

    String table();

    String alias();

    String on();

    Join join() default Join.INNER;

    JoinPosition position() default JoinPosition.NORMAL;

    enum Join {
        INNER, LEFT, RIGHT, FULL
    }

    enum JoinPosition {
        NORMAL, START
    }
}
