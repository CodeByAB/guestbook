package se.webstep.microservice.guestbook.util;

import com.codahale.metrics.MetricRegistry;
import com.google.common.base.Strings;
import com.google.common.collect.ImmutableList;
import io.dropwizard.configuration.ConfigurationFactory;
import io.dropwizard.migrations.CloseableLiquibase;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import se.webstep.microservice.guestbook.MicroServicesConfig;

import javax.validation.Validation;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;

public class DatabaseFixture<T extends MicroServicesConfig> {

    private final T configuration;
    private final String configPath;
    private final DBI database;
    private CloseableLiquibase liquibase = null;

    public DatabaseFixture(Class<T> configurationClass, String configurationPath) throws Exception {
        if (Strings.isNullOrEmpty(configurationPath)) {
            this.configPath = null;
        } else {
            this.configPath = getConfigurationPathFromClasspath(configurationPath);
        }

        this.configuration = parseConfiguration(this.configPath, configurationClass);
        this.database = init();
    }

    public DBI getDatabase() {
        return database;
    }

    public void migrate() throws Exception {
        migrate(null, "");
    }

    public void migrate(Integer count, String context) throws Exception {
        CloseableLiquibase liquibase = getLiquibase();
        if (count != null) {
            liquibase.update(count, context);
        } else {
            liquibase.update(context);
        }
    }

    public void clean() throws Exception {
        getLiquibase().dropAll();
    }

    private String getConfigurationPathFromClasspath(String configurationPath) {
        if (configurationPath == null)
            return null;

        URL resource = getClass().getResource(configurationPath);
        if (resource == null)
            return null;

        return resource.getPath();
    }

    private DBI init() throws Exception {
        Environment env = Environments.create();

        configuration.getDataSourceFactory().setInitialSize(1);
        configuration.getDataSourceFactory().setMinSize(1);
        configuration.getDataSourceFactory().setMaxSize(3);

        return DatabaseFactory.create(env, configuration, "test");
    }

    private CloseableLiquibase getLiquibase() throws Exception {
        if (liquibase == null)
            liquibase = new CloseableLiquibase(configuration.getDataSourceFactory().build(new MetricRegistry(), "liquibase"));

        return liquibase;
    }

    private T parseConfiguration(String filename, Class<T> configurationClass) throws Exception {
        ConfigurationFactory<T> configurationFactory = new ConfigurationFactory<>(configurationClass,
                Validation.buildDefaultValidatorFactory().getValidator(), JsonSupport.objectMapper(), "dw");
        if (filename == null)
            return configurationFactory.build();

        File file = new File(filename);
        if (!file.exists())
            throw new FileNotFoundException("File " + file + " not found!");

        return configurationFactory.build(file);
    }
}
