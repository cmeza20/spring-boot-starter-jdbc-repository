package com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.generics;

import com.cmeza.spring.jdbc.repository.support.definitions.MappingDefinition;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface JdbcGenericBuilder<T> {

    T withParameter(String parameterName, Object parameterValue);

    T withParameter(String parameterName, Object parameterValue, int sqlType);

    T withParameter(Object object);

    T withParameter(Object object, String[] onlyAttributes);

    T withParameterList(List<?> objects);

    T withParameterList(List<?> objects, String[] onlyAttributes);

    T withParameterMap(Map<?, ?> objects);

    T withParameterMap(Map<?, ?> objects, String[] onlyAttributes);

    T withParameterSet(Set<?> objects);

    T withParameterSet(Set<?> objects, String[] onlyAttributes);

    T withRowMapper(RowMapper<?> rowMapper);

    T withRowMapper(Class<? extends RowMapper<?>> rowMapperClass);

    T withKey(String key);

    T loggable(boolean loggable);

    T withMapping(String to, int sqlType);

    T withMapping(String to, String from);

    T withMapping(String to, String from, int sqlType);

    T withMapping(MappingDefinition mappingDefinition);

    T withParamFilter(String... paramFilters);
}
