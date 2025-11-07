package com.cmeza.spring.jdbc.repository.utils;

import com.cmeza.spring.ioc.handler.metadata.TypeMetadata;
import com.cmeza.spring.ioc.handler.metadata.impl.SimpleTypeMetadata;
import com.cmeza.spring.jdbc.repository.mappers.JdbcRowMapper;
import com.cmeza.spring.jdbc.repository.support.definitions.MappingDefinition;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.providers.InParameterSourceProvider;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.providers.MappingSourceProvider;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import java.util.*;
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

    public void printHeaderLog(JdbcHeaderLog jdbcHeaderLog) {
        Logger logger = jdbcHeaderLog.getLogger();
        if (jdbcHeaderLog.isLoggable() && logger.isInfoEnabled()) {
            logger.info("|");
            if (jdbcHeaderLog.hasDatabaseMetadata()) {
                logger.info("| Dialect: {} {}", jdbcHeaderLog.getDatabaseMetaData().getDatabaseProductName(), jdbcHeaderLog.getDatabaseMetaData().getDatabaseProductVersion());
            }
            logger.info("| Executor: {}", jdbcHeaderLog.getClassName());
            logger.info("| JdbcRepositoryTemplate: {}", jdbcHeaderLog.getJdbcRepositoryTemplateBeanName());

            if (jdbcHeaderLog.hasKey()) {
                logger.info("| Key Identification: {}", jdbcHeaderLog.getKey());
            }

            jdbcHeaderLog.getPrintExtras().accept(logger);

            if (jdbcHeaderLog.hasRowMapper()) {
                String rowMapperName = JdbcLoggerUtils.printRowMapper(jdbcHeaderLog.getRowMapper());
                logger.info("| RowMapper: {}", rowMapperName);
            }
        }
    }

    private void findParamInSourceProvider(SqlParameterSource source, int count, Map<SqlParameterSource, List<String>> sourcesFiltered, InParameterSourceProvider inParameterSourceProvider, MappingSourceProvider mappingSourceProvider) {
        for (String paramName : source.getParameterNames()) {
            String paramNameFiltered = paramName;
            if (inParameterSourceProvider.isSetAndNotContains(paramName)){
                paramNameFiltered = null;
            } else if (mappingSourceProvider.isSetMappings()) {
                Optional<MappingDefinition> mappingDefinitionOptional = mappingSourceProvider.findMappingByFromOrTo(paramName);
                paramNameFiltered = mappingDefinitionOptional.map(MappingDefinition::getTo).orElse(paramName);
            }

            if (Objects.isNull(paramNameFiltered)) {
                continue;
            }

            List<String> params = sourcesFiltered.get(source);
            if (Objects.isNull(params)) {
                params = new LinkedList<>();
            }
            params.add(String.format("|      * %s => %s", paramNameFiltered, source.getValue(paramName)));
            sourcesFiltered.put(source, params);
            count++;
        }
    }


    public void printParametersLog(Logger logger, boolean loggable, SqlParameterSource[] sources, InParameterSourceProvider inParameterSourceProvider, MappingSourceProvider mappingSourceProvider) {
        if (loggable && logger.isInfoEnabled()) {

            Map<SqlParameterSource, List<String>> sourcesFiltered = new LinkedHashMap<>();
            int count = 0;
            for (SqlParameterSource source : sources) {
                if (Objects.isNull(source) || Objects.isNull(source.getParameterNames())) {
                    continue;
                }

                findParamInSourceProvider(source, count, sourcesFiltered, inParameterSourceProvider, mappingSourceProvider);
            }

            logger.info("| Parameters: {}", count);

            for (Map.Entry<SqlParameterSource, List<String>> entry : sourcesFiltered.entrySet()) {
                printParameterDetailLog(logger, entry.getKey(), entry.getValue());
            }
        }
    }

    public void printParameterDetailLog(Logger logger, SqlParameterSource source, List<String> lista) {
        if (source.hasValue(CLASS_ID)) {
            Object obj = source.getValue(CLASS_ID);
            if (Objects.nonNull(obj)) {
                logger.info("| - {}", ((Class<?>) obj).getSimpleName());
            }
        }
        if (Objects.nonNull(source.getParameterNames())) {
            for (String name : lista) {
                logger.info(name);
            }
        }
    }

    public void printResult(Logger logger, boolean loggable, Object obj, long mill) {
        if (loggable && logger.isInfoEnabled()) {
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
