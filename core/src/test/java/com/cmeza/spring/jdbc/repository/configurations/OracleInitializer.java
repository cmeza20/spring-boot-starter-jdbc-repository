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
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

@Configuration
@Profile({TestConstants.ORACLE, TestConstants.ALL})
public class OracleInitializer {

    public static final String JDBC_REPOSITORY_TEMPLATE_BEAN = "oracleRepositoryTemplate";
    public static final String JDBC_REPOSITORY_TEMPLATE_POPULATOR = "oracleRepositoryTemplatePopulator";
    public static final String JDBC_TRANSACTION_MANAGER = "oracleTransactionManager";
    public static final String JDBC_DIALECT_NAME = "Oracle";

    @Bean
    @ServiceConnection
    @ConditionalOnProperty(name = "spring.datasource.oracle.docker.enabled", havingValue = "true")
    public OracleContainer oracleContainer() {
        DockerImageName myImage = DockerImageName.parse(oracleDataSourceProperties().getDocker().getName()).asCompatibleSubstituteFor("gvenzl/oracle-xe");

        try (OracleContainer oracleContainer = new OracleContainer(myImage)) {
            oracleContainer.withExposedPorts(1521);
            oracleContainer.withCreateContainerCmdModifier(cmd -> {
                cmd.withHostConfig(
                        new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(oracleDataSourceProperties().getPort()), new ExposedPort(1521)))
                );
            });
            oracleContainer.withEnv("ORACLE_PASSWORD", oracleDataSourceProperties().getPassword());
            oracleContainer.withEnv("APP_USER", oracleDataSourceProperties().getUsername());
            oracleContainer.withEnv("APP_USER_PASSWORD", oracleDataSourceProperties().getPassword());
            oracleContainer.withReuse(true);

            return oracleContainer;
        }
    }

    @Bean
    @ConfigurationProperties("spring.datasource.oracle")
    public CustomDataSourceProperties oracleDataSourceProperties() {
        return new CustomDataSourceProperties();
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
    public CommandLineRunner populator() {
        return args -> {
            ResourceDatabasePopulator schemaPopulator = new ResourceDatabasePopulator();
            if (!oracleDataSourceProperties().getDocker().getEnabled()) {
                schemaPopulator.addScripts(new ClassPathResource("database/oracle/clean.sql"));
            }
            schemaPopulator.addScripts(new ClassPathResource("database/oracle/schema.sql"));
            schemaPopulator.addScripts(new ClassPathResource("database/oracle/oracle_data.sql"));
            schemaPopulator.addScripts(new ClassPathResource("database/oracle/functions.sql"));
            schemaPopulator.addScripts(new ClassPathResource("database/oracle/procedures.sql"));
            schemaPopulator.setSeparator("/");
            schemaPopulator.execute(oracleDataSource());
        };
    }

    @Bean(JDBC_TRANSACTION_MANAGER)
    public PlatformTransactionManager oracleTransactionManager() {
        return new DataSourceTransactionManager(oracleDataSource());
    }
}
