package com.cmeza.spring.jdbc.repository.repositories.executors.types.providers;

import com.cmeza.spring.jdbc.repository.repositories.executors.types.ReturnType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.implementations.QueryImplementation;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcQueryBuilder;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import org.springframework.jdbc.core.RowMapper;

import java.util.Objects;

public enum QueryReturnType implements QueryImplementation<JdbcQueryBuilder> {
    LIST {
        @Override
        public Object execute(JdbcQueryBuilder builder, RowMapper<?> rowMapper, Class<?> resultType) {
            if (Objects.nonNull(rowMapper)) {
                builder.withRowMapper(rowMapper);
                return builder.fetchList();
            }
            return Objects.nonNull(resultType) ? builder.fetchList(resultType) : builder.fetchList();
        }
    },
    SET {
        @Override
        public Object execute(JdbcQueryBuilder builder, RowMapper<?> rowMapper, Class<?> resultType) {
            if (Objects.nonNull(rowMapper)) {
                builder.withRowMapper(rowMapper);
                return builder.fetchSet();
            }
            return Objects.nonNull(resultType) ? builder.fetchSet(resultType) : builder.fetchSet();
        }
    },
    MAP {
        @Override
        public Object execute(JdbcQueryBuilder builder, RowMapper<?> rowMapper, Class<?> resultType) {
            builder.withRowMapper(rowMapper);
            return builder.fetchMap();
        }
    },
    STREAM {
        @Override
        public Object execute(JdbcQueryBuilder builder, RowMapper<?> rowMapper, Class<?> resultType) {
            if (Objects.nonNull(rowMapper)) {
                builder.withRowMapper(rowMapper);
                return builder.fetchStream();
            }
            return Objects.nonNull(resultType) ? builder.fetchStream(resultType) : builder.fetchStream();
        }
    },
    OPTIONAL {
        @Override
        public Object execute(JdbcQueryBuilder builder, RowMapper<?> rowMapper, Class<?> resultType) {
            if (Objects.nonNull(rowMapper)) {
                builder.withRowMapper(rowMapper);
                return builder.fetchOptional();
            }
            return Objects.nonNull(resultType) ? builder.fetchOptional(resultType) : builder.fetchOptional();
        }
    },
    ARRAY {
        @Override
        public Object execute(JdbcQueryBuilder builder, RowMapper<?> rowMapper, Class<?> resultType) {
            if (Objects.nonNull(rowMapper)) {
                builder.withRowMapper(rowMapper);
            }
            return builder.fetchArray(resultType);
        }
    },
    CUSTOM_OBJECT {
        @Override
        public Object execute(JdbcQueryBuilder builder, RowMapper<?> rowMapper, Class<?> resultType) {
            if (Objects.nonNull(rowMapper)) {
                builder.withRowMapper(rowMapper);
                return builder.fetchOne();
            }
            return Objects.nonNull(resultType) ? builder.fetchOne(resultType) : builder.fetchOne();
        }
    },
    ;

    public static QueryReturnType from(ReturnType returnType) {
        return JdbcUtils.returnTypeFromEnumSupplier(QueryReturnType.class, returnType, () -> CUSTOM_OBJECT);
    }
}

