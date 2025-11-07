package com.cmeza.spring.jdbc.repository.repositories.template.dialects.providers;

import com.cmeza.spring.jdbc.repository.mappers.JdbcRowMapper;
import com.cmeza.spring.jdbc.repository.projections.JdbcProjectionRowMapper;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.abstracts.AbstractJdbcBuilder;
import com.cmeza.spring.jdbc.repository.utils.JdbcUtils;
import lombok.Getter;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.util.Assert;

import java.util.Map;
import java.util.Objects;

public class RowMapperSourceProvider<T> {

    private final AbstractJdbcBuilder.Impl impl;
    @Getter
    private RowMapper<?> rowMapper;

    public RowMapperSourceProvider(AbstractJdbcBuilder.Impl impl) {
        this.impl = impl;
    }

    public RowMapperSourceProvider<T> setRowMapper(RowMapper<?> rowMapper) {
        if (Objects.nonNull(rowMapper)) {
            this.rowMapper = rowMapper;
        }
        return this;
    }

    public RowMapperSourceProvider<T> setRowMapper(Class<? extends RowMapper<?>> rowMapperClass) {
        if (Objects.nonNull(rowMapperClass)) {
            this.setRowMapper(impl.getJdbcRepositoryTemplate().registerJdbcRowMapperBean(rowMapperClass));
        }
        return this;
    }

    public boolean isSetRowMapper() {
        return Objects.nonNull(rowMapper);
    }

    public <E> void createRowMapperIfnotExists(Class<E> returnType) {
        if (!isSetRowMapper()) {
            boolean isSingleColumnMapperType = JdbcUtils.isSingleColumnMapperType(returnType);
            if (returnType.isAssignableFrom(Map.class)) {
                setRowMapper(new ColumnMapRowMapper());
            } else if (isSingleColumnMapperType) {
                setRowMapper(new SingleColumnRowMapper<>(returnType));
            } else if (returnType.isInterface()) {
                setRowMapper(JdbcProjectionRowMapper.newInstance(returnType));
            } else {
                setRowMapper(JdbcRowMapper.newInstance(returnType));
            }
        }
    }

    public void incompatibleRowMapperToMap() {
        if (isSetRowMapper() && !rowMapper.getClass().isAssignableFrom(ColumnMapRowMapper.class)) {
            Assert.isNull(rowMapper, "The Map is not compatible with RowMapper");
        }
    }

    public void assertRowMapperRequired() {
        Assert.notNull(rowMapper, "RowMapper required! or specific return type");
    }
}
