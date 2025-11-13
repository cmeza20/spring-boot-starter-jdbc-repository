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
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

@Configuration
@Profile({TestConstants.POSTGRES, TestConstants.ALL})
public class PostgresInitializer {

    public static final String JDBC_REPOSITORY_TEMPLATE_BEAN = "postgresRepositoryTemplate";
    public static final String JDBC_REPOSITORY_TEMPLATE_POPULATOR = "postgresRepositoryTemplatePopulator";
    public static final String JDBC_TRANSACTION_MANAGER = "postgresTransactionManager";
    public static final String JDBC_DIALECT_NAME = "PostgreSQL";

    @Bean
    @ServiceConnection
    @ConditionalOnProperty(name = "spring.datasource.postgres.docker.enabled", havingValue = "true")
    public PostgreSQLContainer<?> postgresContainer() {
        try (PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>(DockerImageName.parse(postgresDataSourceProperties().getDocker().getName()))) {
            postgreSQLContainer.withDatabaseName(postgresDataSourceProperties().getDatabase())
                    .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                            new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(postgresDataSourceProperties().getPort()), new ExposedPort(PostgreSQLContainer.POSTGRESQL_PORT)))
                    ))
                    .withUsername(postgresDataSourceProperties().getUsername())
                    .withPassword(postgresDataSourceProperties().getPassword())
                    .withReuse(true);
            return postgreSQLContainer;
        }
    }

    @Bean
    @ConfigurationProperties("spring.datasource.postgres")
    public CustomDataSourceProperties postgresDataSourceProperties() {
        return new CustomDataSourceProperties();
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
    public CommandLineRunner populator() {
        return args -> {
            ResourceDatabasePopulator schemaPopulator = new ResourceDatabasePopulator();
            schemaPopulator.addScripts(new ClassPathResource("database/postgres/schema.sql"));
            schemaPopulator.addScripts(new ClassPathResource("database/data.sql"));
            schemaPopulator.addScripts(new ClassPathResource("database/postgres/functions.sql"));
            schemaPopulator.addScripts(new ClassPathResource("database/postgres/procedures.sql"));
            schemaPopulator.setSeparator(";;");
            schemaPopulator.execute(postgresDataSource());
        };
    }

    @Bean(JDBC_TRANSACTION_MANAGER)
    public PlatformTransactionManager postgresTransactionManager() {
        return new DataSourceTransactionManager(postgresDataSource());
    }
}
