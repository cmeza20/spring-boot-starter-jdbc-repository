package com.cmeza.spring.jdbc.repository.configurations;

import com.cmeza.spring.jdbc.repository.configurations.properties.CustomDataSourceProperties;
import com.cmeza.spring.jdbc.repository.configurations.providers.InformixContainer;
import com.cmeza.spring.jdbc.repository.constants.TestConstants;
import com.cmeza.spring.jdbc.repository.repositories.template.JdbcRepositoryTemplate;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.transaction.PlatformTransactionManager;
import org.testcontainers.utility.DockerImageName;

import javax.sql.DataSource;

@Configuration
@Profile({TestConstants.INFORMIX, TestConstants.ALL})
public class InformixInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    public static final String JDBC_REPOSITORY_TEMPLATE_BEAN = "informixRepositoryTemplate";
    public static final String JDBC_REPOSITORY_TEMPLATE_POPULATOR = "informixRepositoryTemplatePopulator";
    public static final String JDBC_TRANSACTION_MANAGER = "informixTransactionManager";
    public static final String JDBC_DIALECT_NAME = "Informix Dynamic Server";

    private static InformixContainer<?> informixContainer;

    @Bean
    @ConfigurationProperties("spring.datasource.informix")
    public CustomDataSourceProperties informixDataSourceProperties() {
        return new CustomDataSourceProperties();
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
    public CommandLineRunner populator() {
        return args -> {
            ResourceDatabasePopulator schemaPopulator = new ResourceDatabasePopulator();
            schemaPopulator.addScripts(new ClassPathResource("database/informix/schema.sql"));
            schemaPopulator.addScripts(new ClassPathResource("database/data.sql"));
            schemaPopulator.addScripts(new ClassPathResource("database/informix/procedures.sql"));
            schemaPopulator.setSeparator(";;");
            schemaPopulator.execute(informixDataSource());
        };
    }

    @Bean(JDBC_TRANSACTION_MANAGER)
    public PlatformTransactionManager informixTransactionManager() {
        return new DataSourceTransactionManager(informixDataSource());
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        boolean enabled = environment.getRequiredProperty("spring.datasource.informix.docker.enabled", Boolean.class);
        if (!enabled) return;

        if (informixContainer == null) {

            String dockerName = environment.getRequiredProperty("spring.datasource.informix.docker.name");
            Integer port = environment.getRequiredProperty("spring.datasource.informix.port", Integer.class);
            String database = environment.getRequiredProperty("spring.datasource.informix.database");
            String username = environment.getRequiredProperty("spring.datasource.informix.username");
            String password = environment.getRequiredProperty("spring.datasource.informix.password");

            informixContainer = new InformixContainer<>(DockerImageName.parse(dockerName))
                    .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                            new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(port), new ExposedPort(9088))
                            ))
                    )
                    .withDatabaseName(database)
                    .withUsername(username)
                    .withPassword(password)
                    .withReuse(true);

            informixContainer.start();

        }
    }
}
