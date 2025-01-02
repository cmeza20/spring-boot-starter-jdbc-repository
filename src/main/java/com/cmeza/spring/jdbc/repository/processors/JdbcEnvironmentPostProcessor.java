package com.cmeza.spring.jdbc.repository.processors;

import com.cmeza.spring.jdbc.repository.configurations.JdbcRepositoryProperties;
import com.cmeza.spring.jdbc.repository.repositories.exceptions.JdbcException;
import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.boot.logging.DeferredLog;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePropertySource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

public class JdbcEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final DeferredLog log = new DeferredLog();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            application.addInitializers(ctx -> log.replayTo(JdbcEnvironmentPostProcessor.class));
            String path = environment.getProperty("spring.jdbc.repository.sql-folder", JdbcRepositoryProperties.SQL_DEFAULT_FOLDER);

            Resource[] resources = getResources(application.getClassLoader(), path);
            Map<String, List<Resource>> mapResources = Arrays.stream(resources)
                    .collect(Collectors.groupingBy(r -> FilenameUtils.getExtension(r.getFilename()).toLowerCase()));

            this.ymlPropertiesSource(mapResources.get("yml"), environment);
            this.classicPropertiesSource(mapResources.get("properties"), environment);
            this.mapPropertiesSource(mapResources.get("sql"), environment);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void ymlPropertiesSource(List<Resource> resources, ConfigurableEnvironment environment) {
        if (Objects.nonNull(resources)) {
            resources.forEach(r -> {
                try {
                    PropertySource<?> propertySource = new YamlPropertySourceLoader().load(r.getFilename(), r).get(0);
                    environment.getPropertySources().addAfter(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, propertySource);
                    this.printRegistered(r.getFilename());
                } catch (IOException e) {
                    throw new JdbcException(e);
                }
            });
        }
    }

    private void classicPropertiesSource(List<Resource> resources, ConfigurableEnvironment environment) {
        if (Objects.nonNull(resources)) {
            resources.forEach(r -> {
                try {
                    ResourcePropertySource resourcePropertySource = new ResourcePropertySource(r);
                    environment.getPropertySources().addAfter(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, resourcePropertySource);
                    this.printRegistered(r.getFilename());
                } catch (IOException e) {
                    throw new JdbcException(e);
                }
            });
        }
    }

    private void mapPropertiesSource(List<Resource> resources, ConfigurableEnvironment environment) {
        if (Objects.nonNull(resources)) {
            resources.forEach(r -> this.printRegistered(r.getFilename()));

            Map<String, Object> collect = resources.stream()
                    .collect(Collectors.toMap(Resource::getFilename, this::asString));

            MapPropertySource mapPropertySource = new MapPropertySource("jdbcRepositoryQueries", collect);
            environment.getPropertySources().addAfter(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, mapPropertySource);
        }
    }

    private void printRegistered(String name) {
        log.info("Jdbc Source registered: " + name);
    }

    private Resource[] getResources(ClassLoader classLoader, String path) {
        try {
            PathMatchingResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver(classLoader);
            return patternResolver.getResources(path);
        } catch (Exception e) {
            log.warn("The folder is empty or not found: " + path);
            return new Resource[0];
        }
    }

    private String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}

