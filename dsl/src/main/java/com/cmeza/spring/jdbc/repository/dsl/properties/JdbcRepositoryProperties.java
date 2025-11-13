package com.cmeza.spring.jdbc.repository.dsl.properties;

import com.cmeza.spring.jdbc.repository.support.naming.NamingStrategy;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.Objects;

@Getter
@Validated
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
    private final boolean loggable;

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

    /**
     * Class :: Method :: DSL
     */
    private final Map<String, Map<String, DslProperties>> dsl;

    @ConstructorBinding
    public JdbcRepositoryProperties(
            @DefaultValue(SQL_DEFAULT_FOLDER) String sqlFolder,
            @DefaultValue("false") boolean loggable,
            @DefaultValue("false") boolean onlyDeclaredMethods,
            @DefaultValue("false") boolean onlyMethodDeclaredAnnotations,
            @DefaultValue("false") boolean onlyParameterDeclaredAnnotations,
            @DefaultValue("com.cmeza.spring.jdbc.repository.support.naming.NoOpNamingStrategy") Class<? extends NamingStrategy> namingStrategy,
            Map<String, Map<String, DslProperties>> dsl) {
        this.sqlFolder = sqlFolder;
        this.loggable = loggable;
        this.onlyDeclaredMethods = onlyDeclaredMethods;
        this.onlyMethodDeclaredAnnotations = onlyMethodDeclaredAnnotations;
        this.onlyParameterDeclaredAnnotations = onlyParameterDeclaredAnnotations;
        this.namingStrategy = namingStrategy;
        this.dsl = dsl;
    }

    public DslProperties findDsl(String className, String methodName) {
        if (Objects.isNull(dsl)) {
            return null;
        }
        Map<String, DslProperties> methods = dsl.get(className);
        if (Objects.isNull(methods)) {
            return null;
        }

        return methods.get(methodName);
    }

}
