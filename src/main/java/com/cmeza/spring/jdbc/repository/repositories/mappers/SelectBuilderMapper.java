package com.cmeza.spring.jdbc.repository.repositories.mappers;

import com.cmeza.spring.jdbc.repository.annotations.methods.supports.JdbcCountQuery;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.factories.JdbcSelectFactory;
import com.cmeza.spring.jdbc.repository.repositories.template.dialects.defaults.factories.DefaultSelectFactory;

public class SelectBuilderMapper {

    public JdbcSelectFactory parseFromCountQueryAnnotation(JdbcCountQuery annotation) {
        return new DefaultSelectFactory(annotation.table())
                .withSchema(annotation.schema())
                .withCatalog(annotation.catalog())
                .withColumns(annotation.columns())
                .withAlias(annotation.alias())
                .withWhere(annotation.where())
                .withGroupBy(annotation.groupBy());
    }

}
