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
@Profile({TestConstants.ORACLE, TestConstants.ALL})
public class OracleInitializer {

    public static final String JDBC_REPOSITORY_TEMPLATE_BEAN = "oracleRepositoryTemplate";
    public static final String JDBC_REPOSITORY_TEMPLATE_POPULATOR = "oracleRepositoryTemplatePopulator";
    public static final String JDBC_TRANSACTION_MANAGER = "oracleTransactionManager";
    public static final String JDBC_DIALECT_NAME = "Oracle";

    @Bean
    @ConfigurationProperties("spring.datasource.oracle")
    public DataSourceProperties oracleDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource oracleDataSource() {
        return oracleDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(JDBC_REPOSITORY_TEMPLATE_BEAN)
    public JdbcRepositoryTemplate oracleRepositoryTemplate() {
        return new JdbcRepositoryTemplate(oracleDataSource());
    }

    @Bean(JDBC_REPOSITORY_TEMPLATE_POPULATOR)
    public void populator() {
        ResourceDatabasePopulator schemaPopulator = new ResourceDatabasePopulator();
        schemaPopulator.addScripts(new ClassPathResource("database/oracle/schema.sql"));
        schemaPopulator.addScripts(new ClassPathResource("database/oracle/oracle_data.sql"));
        schemaPopulator.addScripts(new ClassPathResource("database/oracle/functions.sql"));
        schemaPopulator.addScripts(new ClassPathResource("database/oracle/procedures.sql"));
        schemaPopulator.setSeparator("/");
        schemaPopulator.execute(oracleDataSource());
    }

    @Bean(JDBC_TRANSACTION_MANAGER)
    public PlatformTransactionManager oracleTransactionManager() {
        return new DataSourceTransactionManager(oracleDataSource());
    }
}
