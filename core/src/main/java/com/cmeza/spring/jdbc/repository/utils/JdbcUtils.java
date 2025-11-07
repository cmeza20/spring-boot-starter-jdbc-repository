package com.cmeza.spring.jdbc.repository.utils;

import com.cmeza.spring.jdbc.repository.support.exceptions.InvalidProjectionClassException;
import com.cmeza.spring.jdbc.repository.support.exceptions.InvalidReturnTypeException;
import com.cmeza.spring.jdbc.repository.support.exceptions.JdbcException;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.ReturnType;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.jdbc.core.SqlTypeValue;
import org.springframework.jdbc.core.StatementCreatorUtils;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.Types;
import java.util.*;
import java.util.function.Supplier;

@UtilityClass
public final class JdbcUtils {
    private static final Map<Integer, String> sqlTypes;

    static {
        try {
            sqlTypes = new HashMap<>();
            for (Field field : Types.class.getFields()) {
                sqlTypes.put((Integer) field.get(null), field.getName());
            }
        } catch (Exception e) {
            throw new JdbcException("Extract Sql Types Error");
        }
    }

    public Class<?> getGenericClass(Class<?> clazz) {
        Type type = clazz.getGenericSuperclass();
        if (type instanceof ParameterizedType) {
            Type[] actualTypeArguments = ((ParameterizedType) type).getActualTypeArguments();
            if (actualTypeArguments.length > 0) {
                Type actualTypeArgument = actualTypeArguments[0];
                if (actualTypeArgument instanceof ParameterizedType) {
                    return (Class<?>) ((ParameterizedType) actualTypeArgument).getRawType();
                }
                return (Class<?>) actualTypeArgument;
            }
        }
        throw new JdbcException("Type not found!");
    }

    public boolean hasType(int type) {
        return Objects.nonNull(sqlTypes.get(type));
    }

    public String getType(int type) {
        return sqlTypes.get(type);
    }


    public Optional<Object> getBeanFromInterceptor(ApplicationContext applicationContext, Method method) {
        Qualifier qualifier = method.getAnnotation(Qualifier.class);
        if (Objects.nonNull(qualifier)) {
            return Optional.of(applicationContext.getBean(qualifier.value()));
        }

        if (applicationContext.containsBean(method.getName())) {
            return Optional.of(applicationContext.getBean(method.getName()));
        }

        return Optional.empty();
    }

    public <A extends Annotation> A updateAnnotation(A parameterAnnotation, Map<String, Object> values) {
        Map<String, Object> annotationAttributes = AnnotationUtils.getAnnotationAttributes(parameterAnnotation);
        annotationAttributes.putAll(values);

        return createAnnotation(parameterAnnotation.annotationType(), annotationAttributes);
    }

    @SuppressWarnings("unchecked")
    public <A extends Annotation> A createAnnotation(Class<? extends Annotation> annotationType, Map<String, Object> values) {
        return (A) AnnotationUtils.synthesizeAnnotation(
                values,
                annotationType,
                null);
    }

    public boolean isSingleColumnMapperType(Class<?> clazz) {
        return StatementCreatorUtils.javaTypeToSqlParameterType(clazz) != SqlTypeValue.TYPE_UNKNOWN;
    }

    public void projectionClassValidate(Class<?> clazz) {
        Type type = clazz.getGenericSuperclass();
        if (Objects.nonNull(type) && type instanceof ParameterizedType) {
            Arrays.stream(((ParameterizedType) type).getActualTypeArguments()).forEach(t -> {
                if (t instanceof ParameterizedType) {
                    throw new InvalidProjectionClassException(clazz.getSimpleName() + " cannot have generic parameters");
                }
            });
        }
        Type[] genericInterfaces = clazz.getGenericInterfaces();
        Arrays.stream(genericInterfaces).forEach(t -> {
            if (t instanceof ParameterizedType) {
                Arrays.stream(((ParameterizedType) t).getActualTypeArguments()).forEach(u -> {
                    if (u instanceof ParameterizedType) {
                        throw new InvalidProjectionClassException(clazz.getSimpleName() + " cannot have generic parameters");
                    }
                });
            }
        });
    }

    public <T extends Enum<T>> T returnTypeFromEnumThrow(Class<T> enumType, ReturnType returnType) {
        return Arrays.stream(enumType.getEnumConstants())
                .filter(e -> e.name().equals(returnType.name()))
                .findAny()
                .orElseThrow(() -> new InvalidReturnTypeException(enumType.getSimpleName() + "." + returnType.name() + " not exists"));
    }

    public <T extends Enum<T>> T returnTypeFromEnumSupplier(Class<T> enumType, ReturnType returnType, Supplier<T> supplier) {
        return Arrays.stream(enumType.getEnumConstants())
                .filter(e -> e.name().equals(returnType.name()))
                .findAny()
                .orElseGet(supplier);
    }

    public static SqlParameterSource[] createBatch(Object... candidates) {
        return createBatch(Arrays.asList(candidates));
    }

    public static SqlParameterSource[] createBatch(Collection<?> candidates) {
        SqlParameterSource[] batch = new SqlParameterSource[candidates.size()];

        int i = 0;
        for (Iterator<?> var3 = candidates.iterator(); var3.hasNext(); ++i) {
            Object candidate = var3.next();
            batch[i] = candidate instanceof Map ? new JdbcMapSqlParameterSource((Map) candidate) : new JdbcBeanPropertySqlParameterSource(candidate);
        }

        return batch;
    }
}
