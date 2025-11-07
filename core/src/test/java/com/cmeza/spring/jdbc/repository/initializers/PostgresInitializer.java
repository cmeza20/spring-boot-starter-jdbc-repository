package com.cmeza.spring.jdbc.repository.initializers;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@Profile({TestConstants.POSTGRES, TestConstants.ALL})
public class PostgresInitializer {

    public static final String JDBC_REPOSITORY_TEMPLATE_BEAN = "postgresRepositoryTemplate";
    public static final String JDBC_REPOSITORY_TEMPLATE_POPULATOR = "postgresRepositoryTemplatePopulator";
    public static final String JDBC_TRANSACTION_MANAGER = "postgresTransactionManager";
    public static final String JDBC_DIALECT_NAME = "PostgreSQL";

    @Bean
    @ConfigurationProperties("spring.datasource.postgres")
    public DataSourceProperties postgresDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource postgresDataSource() {
        return postgresDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(JDBC_REPOSITORY_TEMPLATE_BEAN)
    public JdbcRepositoryTemplate postgresRepositoryTemplate() {
        return new JdbcRepositoryTemplate(postgresDataSource());
    }

    @Bean(JDBC_REPOSITORY_TEMPLATE_POPULATOR)
    public void populator() {
        ResourceDatabasePopulator schemaPopulator = new ResourceDatabasePopulator();
        schemaPopulator.addScripts(new ClassPathResource("database/postgres/schema.sql"));
        schemaPopulator.addScripts(new ClassPathResource("database/data.sql"));
        schemaPopulator.addScripts(new ClassPathResource("database/postgres/functions.sql"));
        schemaPopulator.addScripts(new ClassPathResource("database/postgres/procedures.sql"));
        schemaPopulator.setSeparator(";;");
        schemaPopulator.execute(postgresDataSource());
    }

    @Bean(JDBC_TRANSACTION_MANAGER)
    public PlatformTransactionManager postgresTransactionManager() {
        return new DataSourceTransactionManager(postgresDataSource());
    }
}
