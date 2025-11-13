package com.cmeza.spring.jdbc.repository.configurations.properties;

import lombok.Data;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

@Data
public class CustomDataSourceProperties extends DataSourceProperties {
    private String database;
    private Integer port;
    private DockerDataSourceProperties docker;

    @Data
    public static class DockerDataSourceProperties {
        private String name;
        private Boolean enabled;
    }
}
