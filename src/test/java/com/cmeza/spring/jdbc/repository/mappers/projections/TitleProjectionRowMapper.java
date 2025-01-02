package com.cmeza.spring.jdbc.repository.mappers.projections;

import com.cmeza.spring.jdbc.repository.projections.JdbcProjectionRowMapper;
import com.cmeza.spring.jdbc.repository.projections.TitleProjection;
import org.springframework.stereotype.Component;

@Component
public class TitleProjectionRowMapper extends JdbcProjectionRowMapper<TitleProjection> {

    protected TitleProjectionRowMapper() {
        super(TitleProjection.class);
    }
}
