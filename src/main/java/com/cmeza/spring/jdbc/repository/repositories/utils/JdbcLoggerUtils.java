package com.cmeza.spring.jdbc.repository.repositories.utils;

import com.cmeza.spring.ioc.handler.metadata.TypeMetadata;
import com.cmeza.spring.ioc.handler.metadata.impl.SimpleTypeMetadata;
import com.cmeza.spring.jdbc.repository.mappers.JdbcRowMapper;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.JdbcDatabaseMatadata;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@UtilityClass
public final class JdbcLoggerUtils {
    private static final String CLASS_ID = "class";

    public String printReturnType(TypeMetadata typeMetadata) {
        StringBuilder stringBuilder = new StringBuilder(typeMetadata.getRawClass().getSimpleName());
        if (typeMetadata.isParameterized()) {
            stringBuilder.append("<")
                    .append(Arrays.stream(typeMetadata.getArgumentTypes()).map(Class::getSimpleName).collect(Collectors.joining(", ")))
                    .append(">");
        }
        return stringBuilder.toString();
    }

    public String printRowMapper(RowMapper<?> jdbcRowMapper) {
        if (Objects.isNull(jdbcRowMapper)) {
            return "Not Present";
        }
        StringBuilder stringBuilder = new StringBuilder(jdbcRowMapper.getClass().getSimpleName());
        if (jdbcRowMapper instanceof JdbcRowMapper) {
            JdbcRowMapper<?> mapper = (JdbcRowMapper<?>) jdbcRowMapper;
            if (Objects.nonNull(mapper.getMappedClass())) {
                stringBuilder.append("<")
                        .append(mapper.getMappedClass().getSimpleName())
                        .append(">");
            }
        }
        return stringBuilder.toString();
    }

    public void printHeaderLog(Logger logger, boolean loggeable, JdbcDatabaseMatadata databaseMetaData, String className, RowMapper<?> rowMapper, Consumer<Logger> printExtras, String jdbcRepositoryTemplateBeanName) {
        if (loggeable && logger.isInfoEnabled()) {
            logger.info("|");
            if (Objects.nonNull(databaseMetaData)) {
                logger.info("| Dialect: {} {}", databaseMetaData.getDatabaseProductName(), databaseMetaData.getDatabaseProductVersion());
            }
            logger.info("| Executor: {}", className);
            logger.info("| JdbcRepositoryTemplate: {}", jdbcRepositoryTemplateBeanName);

            printExtras.accept(logger);

            if (Objects.nonNull(rowMapper)) {
                String rowMapperName = JdbcLoggerUtils.printRowMapper(rowMapper);
                logger.info("| RowMapper: {}", rowMapperName);
            }
        }
    }

    public void printParametersLog(Logger logger, boolean loggeable, SqlParameterSource[] sources) {
        if (loggeable && logger.isInfoEnabled()) {

            logger.info("| Parameters: {}", sources.length);

            int position = 0;
            for (SqlParameterSource source : sources) {
                printParameterDetailLog(logger, sources, source, position);
                position++;
            }
        }
    }

    public void printParameterDetailLog(Logger logger, SqlParameterSource[] sources, SqlParameterSource source, int position) {
        if (source.hasValue(CLASS_ID)) {
            Object obj = source.getValue(CLASS_ID);
            if (Objects.nonNull(obj)) {
                logger.info("| - {}", ((Class<?>) obj).getSimpleName());
            }
        } else if (sources.length > 1) {
            logger.info("| - {}", position);
        }
        if (Objects.nonNull(source.getParameterNames())) {
            for (String name : source.getParameterNames()) {
                if (!name.equals(CLASS_ID)) {
                    logger.info("|      * {} => {}", name, source.getValue(name));
                }
            }
        }
    }

    public void printResult(Logger logger, boolean loggeable, Object obj, long mill) {
        if (loggeable && logger.isInfoEnabled()) {
            logger.info("| Time: {} ms", System.currentTimeMillis() - mill);
            if (obj != null) {
                TypeMetadata typeMetadata = new SimpleTypeMetadata(obj.getClass());
                String resultType = JdbcLoggerUtils.printReturnType(typeMetadata);
                logger.info("| Result Type: {}", resultType);
                logger.info("| Result: {}", obj);
            } else {
                logger.info("| Result: null");
            }
            logger.info("|");
        }
    }
}
