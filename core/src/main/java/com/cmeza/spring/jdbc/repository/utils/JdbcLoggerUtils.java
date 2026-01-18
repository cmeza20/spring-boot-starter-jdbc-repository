package com.cmeza.spring.jdbc.repository.utils;

import com.cmeza.spring.ioc.handler.metadata.TypeMetadata;
import com.cmeza.spring.ioc.handler.metadata.impl.SimpleTypeMetadata;
import com.cmeza.spring.jdbc.repository.mappers.classes.JdbcRowMapper;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.providers.InParameterSourceProvider;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.providers.MappingSourceProvider;
import com.cmeza.spring.jdbc.repository.support.definitions.MappingDefinition;
import lombok.Data;
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
        if (jdbcRowMapper instanceof JdbcRowMapper<?> mapper) {
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

    private void findParamInSourceProvider(SqlParameterSource source, int[] headerCount, int[] attributeCount, Map<SqlParameterSource, SourceDefinition> sourceDefinitionMap, InParameterSourceProvider inParameterSourceProvider, MappingSourceProvider mappingSourceProvider) {
        for (String paramName : source.getParameterNames()) {
            String paramNameFiltered = paramName;
            if (inParameterSourceProvider.isSetAndNotContains(paramName)) {
                paramNameFiltered = null;
            } else if (mappingSourceProvider.isSetMappings()) {
                paramNameFiltered = mappingSourceProvider.findMappingByFromOrTo(paramName)
                        .map(MappingDefinition::getTo).orElse(paramName);
            }

            if (Objects.isNull(paramNameFiltered)) {
                continue;
            }

            SourceDefinition sourceDefinition = sourceDefinitionMap.get(source);
            if (Objects.isNull(sourceDefinition)) {
                sourceDefinition = new SourceDefinition();

                if (source.hasValue(CLASS_ID)) {
                    Object obj = source.getValue(CLASS_ID);
                    if (Objects.nonNull(obj)) {
                        sourceDefinition.setRaw(false);
                        sourceDefinition.setHeader(String.format("| - %s", ((Class<?>) obj).getSimpleName()));
                        headerCount[0]++;
                    }
                }
            }
            sourceDefinition.attribute(String.format("|      * %s => %s", paramNameFiltered, source.getValue(paramName)));

            sourceDefinitionMap.put(source, sourceDefinition);
            attributeCount[0]++;
        }
    }

    public void printParametersLog(Logger logger, boolean loggable, SqlParameterSource[] sources, InParameterSourceProvider inParameterSourceProvider, MappingSourceProvider mappingSourceProvider) {
        if (loggable && logger.isInfoEnabled()) {

            Map<SqlParameterSource, SourceDefinition> sourceDefinitionMap = new LinkedHashMap<>();
            int[] attributeCount = {0};
            int[] headerCount = {0};
            for (SqlParameterSource source : sources) {
                if (Objects.isNull(source) || Objects.isNull(source.getParameterNames())) {
                    continue;
                }

                findParamInSourceProvider(source, headerCount, attributeCount, sourceDefinitionMap, inParameterSourceProvider, mappingSourceProvider);
            }

            logger.info("| Parameters: {}", headerCount[0] > 0 ? headerCount[0] : attributeCount[0]);

            for (SourceDefinition sourceDefinition : sourceDefinitionMap.values()) {
                printParameterDetailLog(logger, sourceDefinition);
            }
        }
    }

    private void printParameterDetailLog(Logger logger, SourceDefinition sourceDefinition) {
        if (!sourceDefinition.isRaw()) {
            logger.info(sourceDefinition.getHeader());
        }

        sourceDefinition.getAttributes().forEach(logger::info);
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

    @Data
    private static class SourceDefinition {
        private final List<String> attributes = new ArrayList<>();
        private boolean raw = true;
        private String header;

        public SourceDefinition attribute(String attribute) {
            attributes.add(attribute);
            return this;
        }
    }
}
