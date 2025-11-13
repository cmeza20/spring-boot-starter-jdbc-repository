package com.cmeza.spring.jdbc.repository.configurations.providers;

import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.utility.DockerImageName;
import org.testcontainers.utility.MountableFile;

import java.nio.file.Path;
import java.text.MessageFormat;
import java.time.Duration;

import static java.time.temporal.ChronoUnit.SECONDS;

@SuppressWarnings("unchecked")
public class InformixContainer<S extends InformixContainer<S>> extends JdbcDatabaseContainer<S> {

    public static final int INFORMIX_PORT = 9088;
    private static final String IFX_CONFIG_DIR = "/opt/ibm/config/";
    private String databaseName = "sysadmin";
    private String username = "informix";
    private String password = "in4mix";

    public InformixContainer(DockerImageName dockerImageName) {
        super(dockerImageName);
        this.waitStrategy = new LogMessageWaitStrategy()
                .withRegEx(".*Maximum server connections 1.*")
                .withTimes(1)
                .withStartupTimeout(Duration.of(60, SECONDS));
        addExposedPort(INFORMIX_PORT);
    }

    @Override
    protected void configure() {
        addEnv("LICENSE", "accept");
    }

    @Override
    public String getDriverClassName() {
        return "com.informix.jdbc.IfxDriver";
    }

    @Override
    public String getJdbcUrl() {
        String additionalUrlParams = constructUrlParameters(";", ";");
        return MessageFormat.format("jdbc:informix-sqli://{0}:{1}/{2}:INFORMIXSERVER=informix{3}",
                getHost(), String.valueOf(getMappedPort(INFORMIX_PORT)), getDatabaseName(), additionalUrlParams);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    protected String getTestQueryString() {
        return "select count(*) from systables";
    }

    @Override
    protected void waitUntilContainerStarted() {
        getWaitStrategy().waitUntilReady(this);
    }

    @Override
    public S withDatabaseName(final String databaseName) {
        this.databaseName = databaseName;
        return (S) this;
    }

    @Override
    public String getDatabaseName() {
        return databaseName;
    }

    public S withInitFile(MountableFile mountableFile) {
        setEnvAndCopyFile(mountableFile, FileType.INIT_FILE);
        return (S) this;
    }

    public S withPostInitFile(MountableFile mountableFile) {
        setEnvAndCopyFile(mountableFile, FileType.RUN_FILE_POST_INIT);
        return (S) this;
    }

    @Override
    public S withUsername(String username) {
        this.username = username;
        return (S) this;
    }

    @Override
    public S withPassword(String password) {
        this.password = password;
        return (S) this;
    }

    private void setEnvAndCopyFile(MountableFile mountableFile, FileType fileType) {
        addEnv(fileType.toString(), Path.of(mountableFile.getFilesystemPath()).getFileName().toString());
        super.withCopyFileToContainer(mountableFile, IFX_CONFIG_DIR);
    }

    private enum FileType {
        INIT_FILE, RUN_FILE_POST_INIT
    }

}