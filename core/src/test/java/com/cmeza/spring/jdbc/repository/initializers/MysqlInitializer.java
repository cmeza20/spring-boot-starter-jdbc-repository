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
@Profile({TestConstants.MYSQL, TestConstants.ALL})
public class MysqlInitializer {

    public static final String JDBC_REPOSITORY_TEMPLATE_BEAN = "mysqlRepositoryTemplate";
    public static final String JDBC_REPOSITORY_TEMPLATE_POPULATOR = "mysqlRepositoryTemplatePopulator";
    public static final String JDBC_TRANSACTION_MANAGER = "mysqlTransactionManager";
    public static final String JDBC_DIALECT_NAME = "MySQL";

    @Bean
    @ConfigurationProperties("spring.datasource.mysql")
    public DataSourceProperties mysqlDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource mysqlDataSource() {
        return mysqlDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Bean(JDBC_REPOSITORY_TEMPLATE_BEAN)
    public JdbcRepositoryTemplate mysqlRepositoryTemplate() {
        return new JdbcRepositoryTemplate(mysqlDataSource());
    }

    @Bean(JDBC_REPOSITORY_TEMPLATE_POPULATOR)
    public void populator() {
        ResourceDatabasePopulator schemaPopulator = new ResourceDatabasePopulator();
        schemaPopulator.addScripts(new ClassPathResource("database/mysql/schema.sql"));
        schemaPopulator.addScripts(new ClassPathResource("database/data.sql"));
        schemaPopulator.addScripts(new ClassPathResource("database/mysql/functions.sql"));
        schemaPopulator.addScripts(new ClassPathResource("database/mysql/procedures.sql"));
        schemaPopulator.setSeparator(";;");
        schemaPopulator.execute(mysqlDataSource());
    }

    @Bean(JDBC_TRANSACTION_MANAGER)
    public PlatformTransactionManager mysqlTransactionManager() {
        return new DataSourceTransactionManager(mysqlDataSource());
    }
}
