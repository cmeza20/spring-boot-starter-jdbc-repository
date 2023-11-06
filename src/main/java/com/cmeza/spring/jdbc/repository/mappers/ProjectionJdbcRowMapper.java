package com.cmeza.spring.jdbc.repository.mappers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.NotWritablePropertyException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

@Slf4j
public class ProjectionJdbcRowMapper<T> implements RowMapper<T> {

    private final Class<T> mappedClass;
    private Map<String, PropertyDescriptor> mappedFields;

    protected ProjectionJdbcRowMapper(Class<T> mappedClass) {
        this.mappedClass = mappedClass;
        initialize();
    }

    @Override
    public T mapRow(ResultSet rs, int rowNum) throws SQLException {

        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();
        Map<String, Object> map = new LinkedHashMap<>();

        for (int index = 1; index <= columnCount; ++index) {
            String column = JdbcUtils.lookupColumnName(rsmd, index);
            String field = this.lowerCaseName(StringUtils.delete(column, " "));
            PropertyDescriptor pd = this.mappedFields != null ? this.mappedFields.get(field) : null;
            if (pd != null) {
                try {
                    Object value = this.getColumnValue(rs, index, pd);
                    if (rowNum == 0 && log.isDebugEnabled()) {
                        log.debug("Mapping column '" + column + "' to property '" + pd.getName() + "' of type '" + ClassUtils.getQualifiedName(pd.getPropertyType()) + "'");
                    }

                    map.put(pd.getName(), value);
                } catch (NotWritablePropertyException var15) {
                    throw new DataRetrievalFailureException("Unable to map column '" + column + "' to property '" + pd.getName() + "'", var15);
                }
            }
        }

        return this.constructMappedInstance(rs, map);
    }

    protected T constructMappedInstance(ResultSet rs, Map<String, Object> map) {
        Assert.state(this.mappedClass != null, "Mapped class was not specified");
        ProjectionFactory pf = new SpelAwareProxyProjectionFactory();
        return pf.createProjection(mappedClass, map);

    }

    protected void initialize() {
        this.mappedFields = new LinkedHashMap<>();
        PropertyDescriptor[] propertyDescriptors = BeanUtils.getPropertyDescriptors(mappedClass);

        for (PropertyDescriptor descriptor : propertyDescriptors) {
            String lowerCaseName = this.lowerCaseName(descriptor.getName());
            this.mappedFields.put(lowerCaseName, descriptor);
            String underscoreName = this.underscoreName(descriptor.getName());
            if (!lowerCaseName.equals(underscoreName)) {
                this.mappedFields.put(underscoreName, descriptor);
            }
        }

    }

    protected String lowerCaseName(String name) {
        return name.toLowerCase(Locale.US);
    }

    protected String underscoreName(String name) {
        if (!StringUtils.hasLength(name)) {
            return "";
        } else {
            StringBuilder result = new StringBuilder();
            result.append(Character.toLowerCase(name.charAt(0)));

            for (int i = 1; i < name.length(); ++i) {
                char c = name.charAt(i);
                if (Character.isUpperCase(c)) {
                    result.append('_').append(Character.toLowerCase(c));
                } else {
                    result.append(c);
                }
            }

            return result.toString();
        }
    }

    protected Object getColumnValue(ResultSet rs, int index, PropertyDescriptor pd) throws SQLException {
        return JdbcUtils.getResultSetValue(rs, index, pd.getPropertyType());
    }
}
