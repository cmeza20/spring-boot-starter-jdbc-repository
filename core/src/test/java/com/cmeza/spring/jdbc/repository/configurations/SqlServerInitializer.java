package com.cmeza.spring.jdbc.repository.configurations;

import com.cmeza.spring.jdbc.repository.configurations.properties.CustomDataSourceProperties;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

@Configuration
@Profile({TestConstants.SQLSERVER, TestConstants.ALL})
public class SqlServerInitializer {

    public static final String JDBC_REPOSITORY_TEMPLATE_BEAN = "sqlserverRepositoryTemplate";
    public static final String JDBC_REPOSITORY_TEMPLATE_POPULATOR = "sqlserverRepositoryTemplatePopulator";
    public static final String JDBC_TRANSACTION_MANAGER = "sqlserverTransactionManager";
    public static final String JDBC_DIALECT_NAME = "Microsoft SQL Server";

    @Bean
    @ServiceConnection
    @ConditionalOnProperty(name = "spring.datasource.sqlserver.docker.enabled", havingValue = "true")
    public MSSQLServerContainer<?> sqlServerContainer() {
        try (MSSQLServerContainer<?> sqlServerContainer = new MSSQLServerContainer<>(DockerImageName.parse(sqlserverDataSourceProperties().getDocker().getName()))) {
            sqlServerContainer.withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                            new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(sqlserverDataSourceProperties().getPort()), new ExposedPort(MSSQLServerContainer.MS_SQL_SERVER_PORT)))
                    ))
                    .withEnv("ACCEPT_EULA", "Y")
                    .withPassword(sqlserverDataSourceProperties().getPassword())
                    .withEnv("MSSQL_SA_PASSWORD", sqlserverDataSourceProperties().getPassword())
                    .withReuse(true)
                    .withInitScript("database/sqlserver/init.sql");

            return sqlServerContainer;
        }
    }

    @Bean
    @ConfigurationProperties("spring.datasource.sqlserver")
    public CustomDataSourceProperties sqlserverDataSourceProperties() {
        return new CustomDataSourceProperties();
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
    public CommandLineRunner populator() {
        return args -> {
            ResourceDatabasePopulator schemaPopulator = new ResourceDatabasePopulator();
            schemaPopulator.addScripts(new ClassPathResource("database/sqlserver/schema.sql"));
            schemaPopulator.addScripts(new ClassPathResource("database/data.sql"));
            schemaPopulator.addScripts(new ClassPathResource("database/sqlserver/functions.sql"));
            schemaPopulator.addScripts(new ClassPathResource("database/sqlserver/procedures.sql"));
            schemaPopulator.setSeparator(";;");
            schemaPopulator.execute(sqlserverDataSource());
        };
    }

    @Bean(JDBC_TRANSACTION_MANAGER)
    public PlatformTransactionManager sqlserverTransactionManager() {
        return new DataSourceTransactionManager(sqlserverDataSource());
    }
}
