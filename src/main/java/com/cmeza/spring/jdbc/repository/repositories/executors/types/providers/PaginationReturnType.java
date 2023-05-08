package com.cmeza.spring.jdbc.repository.repositories.executors.types.providers;

import com.cmeza.spring.jdbc.repository.repositories.executors.types.ReturnType;
import com.cmeza.spring.jdbc.repository.repositories.executors.types.implementations.QueryImplementation;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.JdbcPaginationBuilder;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcUtils;
import org.springframework.jdbc.core.RowMapper;

import java.util.Objects;

public enum PaginationReturnType implements QueryImplementation<JdbcPaginationBuilder> {
    PAGE {
        @Override
        public Object execute(JdbcPaginationBuilder builder, RowMapper<?> rowMapper, Class<?> resultType) {
            if (Objects.nonNull(rowMapper)) {
                builder.withRowMapper(rowMapper);
            }
            return Objects.nonNull(resultType) ? builder.fetchPage() : builder.fetchPage(resultType);
        }
    };

    public static PaginationReturnType from(ReturnType returnType) {
        return JdbcUtils.returnTypeFromEnumThrow(PaginationReturnType.class, returnType);
    }

}
