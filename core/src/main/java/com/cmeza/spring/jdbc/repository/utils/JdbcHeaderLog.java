package com.cmeza.spring.jdbc.repository.utils;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.JdbcDatabaseMatadata;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.jdbc.core.RowMapper;

import java.util.Objects;
import java.util.function.Consumer;

@Data
public class JdbcHeaderLog {
    private Logger logger;
    private boolean loggable;
    private JdbcDatabaseMatadata databaseMetaData;
    private String className;
    private RowMapper<?> rowMapper;
    private Consumer<Logger> printExtras;
    private String jdbcRepositoryTemplateBeanName;
    private String key;

    public boolean hasRowMapper() {
        return Objects.nonNull(rowMapper);
    }

    public boolean hasDatabaseMetadata() {
        return Objects.nonNull(databaseMetaData);
    }

    public boolean hasKey() {
        return !StringUtils.isEmpty(key);
    }

}
