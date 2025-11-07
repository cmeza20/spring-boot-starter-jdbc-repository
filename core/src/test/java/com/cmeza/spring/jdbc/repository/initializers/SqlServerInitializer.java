package com.cmeza.spring.jdbc.repository.initializers;

import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@Profile({TestConstants.SQLSERVER, TestConstants.ALL})
public class SqlServerInitializer {

    public static final String JDBC_REPOSITORY_TEMPLATE_BEAN = "sqlserverRepositoryTemplate";
    public static final String JDBC_REPOSITORY_TEMPLATE_POPULATOR = "sqlserverRepositoryTemplatePopulator";
    public static final String JDBC_TRANSACTION_MANAGER = "sqlserverTransactionManager";
    public static final String JDBC_DIALECT_NAME = "Microsoft SQL Server";

    @Bean
    @ConfigurationProperties("spring.datasource.sqlserver")
    public DataSourceProperties sqlserverDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource sqlserverDataSource() {
        return sqlserverDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(JDBC_REPOSITORY_TEMPLATE_BEAN)
    public JdbcRepositoryTemplate sqlserverRepositoryTemplate() {
        return new JdbcRepositoryTemplate(sqlserverDataSource());
    }

    @Bean(JDBC_REPOSITORY_TEMPLATE_POPULATOR)
    public void populator() {
        ResourceDatabasePopulator schemaPopulator = new ResourceDatabasePopulator();
        schemaPopulator.addScripts(new ClassPathResource("database/sqlserver/schema.sql"));
        schemaPopulator.addScripts(new ClassPathResource("database/data.sql"));
        schemaPopulator.addScripts(new ClassPathResource("database/sqlserver/functions.sql"));
        schemaPopulator.addScripts(new ClassPathResource("database/sqlserver/procedures.sql"));
        schemaPopulator.setSeparator(";;");
        schemaPopulator.execute(sqlserverDataSource());
    }

    @Bean(JDBC_TRANSACTION_MANAGER)
    public PlatformTransactionManager sqlserverTransactionManager() {
        return new DataSourceTransactionManager(sqlserverDataSource());
    }
}
