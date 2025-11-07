package com.cmeza.spring.jdbc.repository.support.properties.methods;

import com.cmeza.spring.jdbc.repository.support.properties.abstracts.AbstractProperties;
import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy;
import com.cmeza.spring.jdbc.repository.support.properties.supports.JdbcCountQueryProperties;
import com.cmeza.spring.jdbc.repository.support.properties.supports.JdbcJoinTableProperties;
import lombok.Data;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Map;

@Data
public class JdbcPaginationProperties extends AbstractProperties {
    private String table;
    private String schema = "";
    private String catalog = "";
    private String alias = "t";
    private String where = "";
    private String[] columns = new String[]{"*"};
    private String[] orderBy = new String[0];
    private String[] groupBy = new String[0];
    private Class<? extends RowMapper> mapper = RowMapper.class;
    private boolean loggable;
    private Class<? extends NamingStrategy> columnsNamingStrategy = NoOpNamingStrategy.class;

    private JdbcCountQueryProperties countQuery;
    private List<JdbcJoinTableProperties> joinTables;

    @Override
    public void mapProperties(Map<String, Object> map) {
        map.put("table", table);
        map.put("schema", schema);
        map.put("catalog", catalog);
        map.put("alias", alias);
        map.put("where", where);
        map.put("columns", columns);
        map.put("orderBy", orderBy);
        map.put("groupBy", groupBy);
        map.put("mapper", mapper);
        map.put("loggable", loggable);
        map.put("columnsNamingStrategy", columnsNamingStrategy);
    }
}
