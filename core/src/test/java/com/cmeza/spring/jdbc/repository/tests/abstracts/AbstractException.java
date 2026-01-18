package com.cmeza.spring.jdbc.repository.tests.abstracts;

import com.cmeza.spring.jdbc.repository.utils.AssertUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

@Slf4j
public abstract class AbstractException {

    protected <T extends Throwable> void testException(Class<T> expectedType, Executable executable) {
        testException(expectedType, executable, null, null);
    }

    protected <T extends Throwable> void testException(Class<T> expectedType, Executable executable, String title) {
        testException(expectedType, executable, title, null);
    }

    protected <T extends Throwable> void testException(Class<T> expectedType, Executable executable, String title, String message) {
        if (StringUtils.isNotEmpty(title)) {
            log.info("[{}] expected type -> {}", title, expectedType);
        }
        T exception = Assertions.assertThrowsExactly(expectedType, executable);

        if (StringUtils.isNotEmpty(title)) {
            log.info("[{}] exception message -> {}", title, exception.getMessage());
        }

        if (StringUtils.isNotEmpty(title) && StringUtils.isNotEmpty(message)) {
            log.info("[{}] expected message -> {}", title, message);
            AssertUtils.assertEquals(exception.getMessage(), message, String.class);
        }
    }

    protected Class<?> classId() {
        return Integer.class;
    }

    @SuppressWarnings("unchecked")
    protected <T> T equalsNumber(String obj, Class<T> clazz) {
        if (clazz.equals(Integer.class)) {
            return (T) Integer.valueOf(obj);
        } else if (clazz.equals(Long.class)) {
            return (T) Long.valueOf(obj);
        } else if (clazz.equals(BigDecimal.class)) {
            return (T) BigDecimal.valueOf(Long.parseLong(obj));
        }
        return (T) obj;
    }
}
