package com.cmeza.spring.jdbc.repository.configurations;

import com.cmeza.spring.jdbc.repository.naming.NamingStrategy;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Getter
@Validated
@ConstructorBinding
@ConfigurationProperties("spring.jdbc.repository")
public final class JdbcRepositoryProperties {

    public static final String SQL_DEFAULT_FOLDER = "classpath:/jdbc/**";

    /**
     * Folder where the queries and properties are stored (String)
     */
    @NotNull
    private final String sqlFolder;

    /**
     * Global track logger (Boolean)
     */
    private final boolean loggeable;

    /**
     * Search only declared methods (Boolean)
     */
    private final boolean onlyDeclaredMethods;

    /**
     * Search only for annotations declared in the method (Boolean)
     */
    private final boolean onlyMethodDeclaredAnnotations;

    /**
     * Search only annotations declared in the parameter (Boolean)
     */
    private final boolean onlyParameterDeclaredAnnotations;

    /**
     * Global naming strategy (NamingStrategy)
     */
    private final Class<? extends NamingStrategy> namingStrategy;

    public JdbcRepositoryProperties(
            @DefaultValue(SQL_DEFAULT_FOLDER) String sqlFolder,
            @DefaultValue("false") boolean loggeable,
            @DefaultValue("false") boolean onlyDeclaredMethods,
            @DefaultValue("false") boolean onlyMethodDeclaredAnnotations,
            @DefaultValue("false") boolean onlyParameterDeclaredAnnotations,
            @DefaultValue("com.cmeza.spring.jdbc.repository.naming.NoOpNamingStrategy") Class<? extends NamingStrategy> namingStrategy) {
        this.sqlFolder = sqlFolder;
        this.loggeable = loggeable;
        this.onlyDeclaredMethods = onlyDeclaredMethods;
        this.onlyMethodDeclaredAnnotations = onlyMethodDeclaredAnnotations;
        this.onlyParameterDeclaredAnnotations = onlyParameterDeclaredAnnotations;
        this.namingStrategy = namingStrategy;
    }

}
