package com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders;

import com.cmeza.spring.jdbc.repository.repositories.template.dialects.builders.generics.JdbcGenericBuilder;
import org.springframework.jdbc.support.KeyHolder;

public interface JdbcInsertBuilder extends JdbcGenericBuilder<JdbcInsertBuilder> {
    int execute();

    int[] executeBatch();

    KeyHolder executeAndReturnKeyHolder();

    JdbcInsertBuilder withSchema(String schema);

    JdbcInsertBuilder withCatalog(String catalog);

    JdbcInsertBuilder withColumns(String... columns);

    JdbcInsertBuilder withGeneratedKeyColumns(String... generatedKeyColumns);

    JdbcInsertBuilder withAccessTableColumnMetaData(boolean value);

    JdbcInsertBuilder withOverrideIncludeSynonymsDefault(boolean value);
}
