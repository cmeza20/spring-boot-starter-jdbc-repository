package com.cmeza.spring.jdbc.repository.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.assertj.core.api.Assertions;

import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class AssertUtils {

    public static <T> void assertCollection(Collection<T> collection) {
        Assertions.assertThat(collection).isNotNull();
    }

    public static <T> void assertCollection(Collection<T> collection, int size) {
        assertCollection(collection);
        Assertions.assertThat(collection).hasSize(size);
    }

    public static <T> void assertArray(T[] array) {
        Assertions.assertThat(array).isNotNull();
    }

    public static <T> void assertArray(int[] array) {
        Assertions.assertThat(array).isNotNull();
    }

    public static <T> void assertArray(T[] array, int size) {
        assertArray(array);
        Assertions.assertThat(array).hasSize(size);
    }

    public static <T> void assertArray(int[] array, int size) {
        assertArray(array);
        Assertions.assertThat(array).hasSize(size);
    }

    public static <T> void assertMap(Map<String, Object> map) {
        Assertions.assertThat(map).isNotNull();
        Assertions.assertThat(map).isInstanceOfAny(Map.class);
    }

    public static <T> void assertStream(Stream<T> stream) {
        Assertions.assertThat(stream).isNotNull();
    }

    public static <T> void assertStream(Stream<T> stream, int size) {
        assertStream(stream);
        Assertions.assertThat(stream).hasSize(size);
    }

    public static <T> void assertObject(T employee, Class<?> clazz) {
        Assertions.assertThat(employee).isNotNull();
        Assertions.assertThat(employee).isInstanceOf(clazz);
    }

    public static <T> void assertOptional(Optional<T> employee, Class<?> clazz) {
        Assertions.assertThat(employee).isNotNull();
        Assertions.assertThat(employee).isPresent();
        Assertions.assertThat(employee.get()).isInstanceOf(clazz);
    }

    public static <T> void assertEquals(T object, Object eq, Class<?> clazz) {
        Assertions.assertThat(object).isNotNull();
        Assertions.assertThat(object).isInstanceOf(clazz);
        Assertions.assertThat(object).isEqualTo(eq);
    }

    public static <T> void assertNotNull(T object) {
        Assertions.assertThat(object).isNotNull();
    }
}
