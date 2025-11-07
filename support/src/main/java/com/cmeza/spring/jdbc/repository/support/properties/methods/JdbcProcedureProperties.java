package com.cmeza.spring.jdbc.repository.support.properties.methods;

import com.cmeza.spring.jdbc.repository.support.properties.abstracts.AbstractProperties;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.support.properties.parameters.ParameterProperties;
import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
public class JdbcProcedureProperties extends AbstractProperties {
    private String name;
    private String schema = "";
    private String catalog = "";
    private String[] inParameterNames = new String[0];
    private List<ParameterProperties> outParameters;
    private String rowMapperParameterName = "result";
    private Class<? extends RowMapper> mapper = RowMapper.class;
    private boolean accessCallParameterMetaData;
    private boolean loggable;
    private Class<? extends NamingStrategy> parametersNamingStrategy = NoOpNamingStrategy.class;

    @Override
    public void mapProperties(Map<String, Object> map) {
        List<Map<String, Object>> parametersResult = new ArrayList<>();

        if (Objects.nonNull(outParameters)) {
            outParameters.forEach(parameterDsl ->
                    parametersResult.add(parameterDsl.transform()));
        }

        map.put("name", name);
        map.put("schema", schema);
        map.put("catalog", catalog);
        map.put("inParameterNames", inParameterNames);
        map.put("outParameters", parametersResult);
        map.put("rowMapperParameterName", rowMapperParameterName);
        map.put("mapper", mapper);
        map.put("accessCallParameterMetaData", accessCallParameterMetaData);
        map.put("loggable", loggable);
        map.put("parametersNamingStrategy", parametersNamingStrategy);
    }
}
