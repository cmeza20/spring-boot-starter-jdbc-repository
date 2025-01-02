package com.cmeza.spring.jdbc.repository.repositories.template.dialects.providers;

import com.cmeza.spring.jdbc.repository.repositories.definitions.MappingDefinition;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcBeanPropertiesMapping;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcMapSqlParameterSource;
import com.cmeza.spring.jdbc.repository.repositories.utils.JdbcUtils;
import lombok.Getter;
import org.apache.logging.log4j.util.TriConsumer;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.util.Assert;

import java.sql.Types;
import java.util.*;
import java.util.stream.Stream;

public class ParameterSourceProvider {

    @Getter
    private final JdbcMapSqlParameterSource mapParameterSource;
    @Getter
    private final List<SqlParameterSource> beanParameterSources;
    private final InParameterSourceProvider inParameterSourceProvider;
    private final MappingSourceProvider mappingSourceProvider;

    public ParameterSourceProvider(InParameterSourceProvider inParameterSourceProvider, MappingSourceProvider mappingSourceProvider) {
        mapParameterSource = new JdbcMapSqlParameterSource();
        beanParameterSources = new ArrayList<>();
        this.inParameterSourceProvider = inParameterSourceProvider;
        this.mappingSourceProvider = mappingSourceProvider;
    }

    public void addParameterSource(String parameterName, Object parameterValue, int sqlType) {
        addParameterSource(parameterName, parameterValue, sqlType, this::addParameterSourceValue);
    }

    public void addParameterSource(String parameterName, Object parameterValue, int sqlType, TriConsumer<String, Object, Integer> consumer) {
        String parameterNameTo = parameterName;
        int sqlTypeTo = sqlType;

        if (inParameterSourceProvider.isSetAndNotContains(parameterName)) {
            parameterNameTo = null;

        } else if (mappingSourceProvider.isSetMappings()) {
            Optional<MappingDefinition> mappingDefinitionOptional = mappingSourceProvider.findMappingByFrom(parameterName);
            if (mappingDefinitionOptional.isPresent()) {
                MappingDefinition mappingDefinition = mappingDefinitionOptional.get();
                parameterNameTo = mappingDefinition.getTo();
                sqlTypeTo = mappingDefinition.getType();
            } else {
                parameterNameTo = null;
            }
        }

        if (Objects.nonNull(parameterNameTo)) {
            consumer.accept(parameterNameTo, parameterValue, sqlTypeTo);
        }
    }

    public void addParameterSourceValue(String parameterNameTo, Object parameterValue, int sqlType) {
        if (Objects.nonNull(parameterValue) && parameterValue.getClass().isArray()) {
            if (sqlType == Types.NULL) {
                mapParameterSource.addValue(parameterNameTo, Arrays.asList((Object[]) parameterValue));
            } else {
                mapParameterSource.addValue(parameterNameTo, Arrays.asList((Object[]) parameterValue), sqlType);
            }
        } else {
            if (sqlType == Types.NULL) {
                mapParameterSource.addValue(parameterNameTo, parameterValue);
            } else {
                mapParameterSource.addValue(parameterNameTo, parameterValue, sqlType);
            }
        }
    }

    public SqlParameterSource[] getParameterSources() {
        return Stream.concat(beanParameterSources.stream(), mapParameterSource.getValues().isEmpty() ? Stream.empty() : Stream.of(mapParameterSource)).toArray(SqlParameterSource[]::new);
    }

    public MapSqlParameterSource getMergedSqlParameterSource() {
        MapSqlParameterSource sqlParameterSource = new MapSqlParameterSource(mapParameterSource.getValues());
        beanParameterSources.forEach(bean ->
                Arrays.stream(Objects.requireNonNull(bean.getParameterNames()))
                        .forEach(name -> addParameterSource(name, bean.getValue(name), bean.getSqlType(name), sqlParameterSource::addValue)));
        return sqlParameterSource;
    }

    public ParameterSourceProvider addBeanParameterSourceList(List<?> object) {
        addBeanParameterSource(JdbcUtils.createBatch(object));
        return this;
    }

    public ParameterSourceProvider addBeanParameterSourceMap(Map<?, ?> object) {
        addBeanParameterSource(JdbcUtils.createBatch(object));
        return this;
    }

    public ParameterSourceProvider addBeanParameterSourceSet(Set<?> object) {
        addBeanParameterSource(JdbcUtils.createBatch(object));
        return this;
    }

    public ParameterSourceProvider addBeanParameterSource(Object object) {
        addBeanParameterSource(JdbcUtils.createBatch(object));
        return this;
    }

    public ParameterSourceProvider addBeanParameterSource(SqlParameterSource[] sqlParameterSources) {
        if (Objects.nonNull(sqlParameterSources)) {
            for (SqlParameterSource parameterSource : sqlParameterSources) {
                iterateParameterSource(parameterSource);
            }

        }
        return this;
    }

    public void assertParameterSourcesRequired() {
        Assert.isTrue(!(mapParameterSource.getValues().isEmpty() && beanParameterSources.isEmpty()), "ParameterSources required!");
    }

    private void iterateParameterSource(SqlParameterSource parameterSource) {
        String[] parameterNames = parameterSource.getParameterNames();
        if (parameterSource instanceof JdbcBeanPropertiesMapping && mappingSourceProvider.isSetMappings() && Objects.nonNull(parameterNames)) {

            for (String parameterName : parameterNames) {
                Optional<MappingDefinition> mappingDefinitionOptional = mappingSourceProvider.findMappingByFrom(parameterName);
                if (mappingDefinitionOptional.isPresent()) {
                    MappingDefinition mappingDefinition = mappingDefinitionOptional.get();
                    ((JdbcBeanPropertiesMapping)parameterSource).addMappingDefinition(mappingDefinition);
                }
            }

        }
        beanParameterSources.add(parameterSource);
    }

}
