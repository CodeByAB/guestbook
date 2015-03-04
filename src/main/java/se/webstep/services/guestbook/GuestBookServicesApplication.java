package se.webstep.services.guestbook;

import io.dropwizard.Application;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;
import se.webstep.services.guestbook.health.SimpleHealthCheck;
import se.webstep.services.guestbook.jdbi.WriterDao;
import se.webstep.services.guestbook.resource.WriterResource;

public class GuestBookServicesApplication extends Application<GuestBookServicesConfig> {

    private DBI jdbi;

    public static void main(String[] args)  throws Exception {
        new GuestBookServicesApplication().run(args);
    }

    @Override
    public void initialize(Bootstrap<GuestBookServicesConfig> bootstrap) {
        bootstrap.addBundle(new MigrationsBundle<GuestBookServicesConfig>() {
            @Override
            public DataSourceFactory getDataSourceFactory(GuestBookServicesConfig configuration) {
                return configuration.getDataSourceFactory();
            }
        });
    }

    @Override
    public void run(GuestBookServicesConfig config, Environment environment) throws Exception {
        final DBIFactory factory = new DBIFactory();
        this.jdbi = factory.build(environment, config.getDataSourceFactory(), "db");

        environment.jersey().register(new WriterResource(this));
        environment.healthChecks().register("simple", new SimpleHealthCheck());

        final WriterDao dao = jdbi.onDemand(WriterDao.class);

    }

    @Override
    public String getName() {
        return "Guestbook";
    }

    public DBI getJdbi(){
        return jdbi;
    }

}
