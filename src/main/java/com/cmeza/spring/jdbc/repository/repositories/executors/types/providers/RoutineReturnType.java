package com.cmeza.spring.jdbc.repository.repositories.executors.types.providers;

import com.cmeza.spring.jdbc.repository.repositories.executors.types.ReturnType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.implementations.QueryImplementation;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcRoutineBuilder;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcUtils;
import org.springframework.jdbc.core.RowMapper;

import java.util.Objects;

public enum RoutineReturnType implements QueryImplementation<JdbcRoutineBuilder> {
    LIST {
        @Override
        public Object execute(JdbcRoutineBuilder builder, RowMapper<?> rowMapper, Class<?> resultType) {
            builder.withRowMapper(rowMapper);
            return Objects.nonNull(resultType) ? builder.executeList(resultType) : builder.executeList();
        }
    },
    SET {
        @Override
        public Object execute(JdbcRoutineBuilder builder, RowMapper<?> rowMapper, Class<?> resultType) {
            builder.withRowMapper(rowMapper);
            return Objects.nonNull(resultType) ? builder.executeSet(resultType) : builder.executeSet();
        }
    },
    MAP {
        @Override
        public Object execute(JdbcRoutineBuilder builder, RowMapper<?> rowMapper, Class<?> resultType) {
            builder.withRowMapper(rowMapper);
            return builder.executeMap();
        }
    },
    STREAM {
        @Override
        public Object execute(JdbcRoutineBuilder builder, RowMapper<?> rowMapper, Class<?> resultType) {
            builder.withRowMapper(rowMapper);
            return Objects.nonNull(resultType) ? builder.executeStream(resultType) : builder.executeStream();
        }
    },
    OPTIONAL {
        @Override
        public Object execute(JdbcRoutineBuilder builder, RowMapper<?> rowMapper, Class<?> resultType) {
            builder.withRowMapper(rowMapper);
            return Objects.nonNull(resultType) ? builder.executeOptional(resultType) : builder.executeOptional();
        }
    },
    ARRAY {
        @Override
        public Object execute(JdbcRoutineBuilder builder, RowMapper<?> rowMapper, Class<?> resultType) {
            builder.withRowMapper(rowMapper);
            return builder.executeArray(resultType);
        }
    },
    CUSTOM_OBJECT {
        @Override
        public Object execute(JdbcRoutineBuilder builder, RowMapper<?> rowMapper, Class<?> resultType) {
            builder.withRowMapper(rowMapper);
            return Objects.nonNull(resultType) ? builder.execute(resultType) : builder.execute();
        }
    };

    public static RoutineReturnType from(ReturnType returnType) {
        return JdbcUtils.returnTypeFromEnumSupplier(RoutineReturnType.class, returnType, () -> CUSTOM_OBJECT);
    }
}

