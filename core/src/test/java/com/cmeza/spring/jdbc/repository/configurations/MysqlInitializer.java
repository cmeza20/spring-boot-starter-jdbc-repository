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
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

@Configuration
@Profile({TestConstants.MYSQL, TestConstants.ALL})
public class MysqlInitializer {

    public static final String JDBC_REPOSITORY_TEMPLATE_BEAN = "mysqlRepositoryTemplate";
    public static final String JDBC_REPOSITORY_TEMPLATE_POPULATOR = "mysqlRepositoryTemplatePopulator";
    public static final String JDBC_TRANSACTION_MANAGER = "mysqlTransactionManager";
    public static final String JDBC_DIALECT_NAME = "MySQL";

    @Bean
    @ServiceConnection
    @ConditionalOnProperty(name = "spring.datasource.mysql.docker.enabled", havingValue = "true")
    public MySQLContainer<?> mysqlContainer() {
        try (MySQLContainer<?> mySQLContainer = new MySQLContainer<>(DockerImageName.parse(mysqlDataSourceProperties().getDocker().getName()))) {
            mySQLContainer.withDatabaseName(mysqlDataSourceProperties().getDatabase())
                    .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                            new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(mysqlDataSourceProperties().getPort()), new ExposedPort(MySQLContainer.MYSQL_PORT)))
                    ))
                    .withUsername(mysqlDataSourceProperties().getUsername())
                    .withPassword(mysqlDataSourceProperties().getPassword())
                    .withReuse(true);
            return mySQLContainer;
        }
    }

    @Bean
    @ConfigurationProperties("spring.datasource.mysql")
    public CustomDataSourceProperties mysqlDataSourceProperties() {
        return new CustomDataSourceProperties();
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
    public CommandLineRunner populator() {
        return args -> {
            ResourceDatabasePopulator schemaPopulator = new ResourceDatabasePopulator();
            schemaPopulator.addScripts(new ClassPathResource("database/mysql/schema.sql"));
            schemaPopulator.addScripts(new ClassPathResource("database/data.sql"));
            schemaPopulator.addScripts(new ClassPathResource("database/mysql/functions.sql"));
            schemaPopulator.addScripts(new ClassPathResource("database/mysql/procedures.sql"));
            schemaPopulator.setSeparator(";;");
            schemaPopulator.execute(mysqlDataSource());
        };
    }

    @Bean(JDBC_TRANSACTION_MANAGER)
    public PlatformTransactionManager mysqlTransactionManager() {
        return new DataSourceTransactionManager(mysqlDataSource());
    }
}
