package com.cmeza.spring.jdbc.repository.tests.utils;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.core.env.AbstractEnvironment;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repeatable(EnabledIfProfiles.class)
@ExtendWith({EnabledIfProfileCondition.class})
public @interface EnabledIfProfile {
    String named() default AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME;

    String[] profiles();

    String inheritedProfile() default TestConstants.ALL;
}
