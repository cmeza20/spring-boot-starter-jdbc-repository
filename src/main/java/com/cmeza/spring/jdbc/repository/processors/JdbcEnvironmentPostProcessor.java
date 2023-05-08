package com.cmeza.spring.jdbc.repository.processors;

import com.cmeza.spring.jdbc.repository.configurations.JdbcRepositoryProperties;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.io.support.ResourcePropertySource;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class JdbcEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private final Function<Resource, File> extractFile = resource -> {
        try {
            return resource.getFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };
    private final Function<File, String> readFile = file -> {
        try {
            return FileUtils.readFileToString(file, StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    };

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        try {
            String path = environment.getProperty("spring.jdbc.repository.sqlFolder", JdbcRepositoryProperties.SQL_DEFAULT_FOLDER);
            ResourcePatternResolver patternResolver = new PathMatchingResourcePatternResolver(application.getClassLoader());

            Resource[] resources = patternResolver.getResources(path);

            Map<String, List<Resource>> mapResources = Arrays.stream(resources)
                    .collect(Collectors.groupingBy(r -> FilenameUtils.getExtension(extractFile.apply(r).getName()).toLowerCase()));

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
                    throw new RuntimeException(e);
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
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void mapPropertiesSource(List<Resource> resources, ConfigurableEnvironment environment) {
        if (Objects.nonNull(resources)) {
            Map<String, Object> collect = resources.stream()
                    .peek(r -> this.printRegistered(r.getFilename()))
                    .collect(Collectors.toMap(r -> extractFile.apply(r).getName(), r -> readFile.apply(extractFile.apply(r))));


            MapPropertySource mapPropertySource = new MapPropertySource("jdbcRepositoryQueries", collect);
            environment.getPropertySources().addAfter(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, mapPropertySource);
        }
    }

    private void printRegistered(String name) {
        System.out.println("Jdbc Source registered: " + name);
    }

}

