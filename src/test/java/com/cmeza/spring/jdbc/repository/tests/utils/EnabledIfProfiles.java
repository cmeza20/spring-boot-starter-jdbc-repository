package com.cmeza.spring.jdbc.repository.tests.utils;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EnabledIfProfiles {
    EnabledIfProfile[] value();
}
