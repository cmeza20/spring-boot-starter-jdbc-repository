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
@Profile({TestConstants.INFORMIX, TestConstants.ALL})
public class InformixInitializer {

    public static final String JDBC_REPOSITORY_TEMPLATE_BEAN = "informixRepositoryTemplate";
    public static final String JDBC_REPOSITORY_TEMPLATE_POPULATOR = "informixRepositoryTemplatePopulator";
    public static final String JDBC_TRANSACTION_MANAGER = "informixTransactionManager";
    public static final String JDBC_DIALECT_NAME = "Informix Dynamic Server";

    @Bean
    @ConfigurationProperties("spring.datasource.informix")
    public DataSourceProperties informixDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource informixDataSource() {
        return informixDataSourceProperties().initializeDataSourceBuilder().build();
    }

    @Primary
    @Bean(JDBC_REPOSITORY_TEMPLATE_BEAN)
    public JdbcRepositoryTemplate informixRepositoryTemplate() {
        return new JdbcRepositoryTemplate(informixDataSource());
    }

    @Bean(JDBC_REPOSITORY_TEMPLATE_POPULATOR)
    public void populator() {
        ResourceDatabasePopulator schemaPopulator = new ResourceDatabasePopulator();
        schemaPopulator.addScripts(new ClassPathResource("database/informix/schema.sql"));
        schemaPopulator.addScripts(new ClassPathResource("database/data.sql"));
        schemaPopulator.addScripts(new ClassPathResource("database/informix/procedures.sql"));
        schemaPopulator.setSeparator(";;");
        schemaPopulator.execute(informixDataSource());
    }

    @Bean(JDBC_TRANSACTION_MANAGER)
    public PlatformTransactionManager informixTransactionManager() {
        return new DataSourceTransactionManager(informixDataSource());
    }
}
