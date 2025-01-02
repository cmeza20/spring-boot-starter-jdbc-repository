package com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.generics;

import com.cmeza.spring.jdbc.repository.repositories.definitions.MappingDefinition;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface JdbcGenericBuilder<T> {

    T withParameter(String parameterName, Object parameterValue);

    T withParameter(String parameterName, Object parameterValue, int sqlType);

    T withParameter(Object object);

    T withParameterList(List<?> objects);

    T withParameterMap(Map<?, ?> objects);

    T withParameterSet(Set<?> objects);

    T withRowMapper(RowMapper<?> rowMapper);

    T withRowMapper(Class<? extends RowMapper<?>> rowMapperClass);

    T withKey(String key);

    T loggeable(boolean loggeable);

    T withMapping(String to, int sqlType);

    T withMapping(String to, String from);

    T withMapping(String to, String from, int sqlType);

    T withMapping(MappingDefinition mappingDefinition);
}
