package com.cmeza.spring.jdbc.repository.mappers.projections;

import com.cmeza.spring.jdbc.repository.projections.EmployeeAndTitleProjection;
import com.cmeza.spring.jdbc.repository.projections.JdbcProjectionRowMapper;
import com.cmeza.spring.jdbc.repository.projections.ProjectionAttributes;
import org.springframework.stereotype.Component;

import java.beans.PropertyDescriptor;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class EmployeeAndTitleProjectionRowMapper extends JdbcProjectionRowMapper<EmployeeAndTitleProjection> {

    private final TitleProjectionRowMapper titleProjectionRowMapper;

    protected EmployeeAndTitleProjectionRowMapper(TitleProjectionRowMapper titleProjectionRowMapper) {
        super(EmployeeAndTitleProjection.class);
        this.titleProjectionRowMapper = titleProjectionRowMapper;
    }

    @Override
    protected void mapPropertyDescriptor(ResultSet rs, PropertyDescriptor propertyDescriptor, ProjectionAttributes projectionAttributes, int rowNum) throws SQLException {
        projectionAttributes.addAttribute("titleObject", titleProjectionRowMapper.mapRow(rs, rowNum));
    }

    @Override
    public EmployeeAndTitleProjection mapRow(ResultSet rs, int rowNum) throws SQLException {
        return super.mapRow(rs, rowNum);
    }
}
