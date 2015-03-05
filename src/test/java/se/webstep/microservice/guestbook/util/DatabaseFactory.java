package se.webstep.microservice.guestbook.util;

import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import se.webstep.microservice.guestbook.MicroServicesConfig;

public class DatabaseFactory {

    private DatabaseFactory() {
    }

    public static DBI create(Environment environment, MicroServicesConfig configuration, String name) throws ClassNotFoundException {
        DBIFactory factory = new DBIFactory();
        DBI jdbi = factory.build(environment, configuration.getDataSourceFactory(), name);
        return jdbi;
    }

}
