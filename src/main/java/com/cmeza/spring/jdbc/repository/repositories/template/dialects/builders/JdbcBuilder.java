package com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders;

import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface JdbcBuilder<T> {

    T withParameter(String parameterName, Object parameterValue);

    T withParameter(String parameterName, Object parameterValue, int sqlType);

    T withParameter(Object object);

    T withParameterList(List<?> objects);

    T withParameterMap(Map<?, ?> objects);

    T withParameterSet(Set<?> objects);

    T withRowMapper(RowMapper<?> rowMapper);

    T loggeable(boolean loggeable);

}
