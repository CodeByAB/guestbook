package se.webstep.microservice.guestbook.util;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.junit.rules.ExternalResource;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.tweak.HandleCallback;
import se.webstep.microservice.guestbook.MicroServicesConfig;

public class TestDatabase<T extends MicroServicesConfig> extends ExternalResource {

    private DatabaseFixture<T> databaseFixture;
    private static CacheKey lastKey;
    private static TestDatabase<? extends MicroServicesConfig> lastDatabase;

    @SuppressWarnings("unchecked")
    public static <T extends MicroServicesConfig> TestDatabase<T> forConfiguration(Class<T> configurationClass,
                                                                                   String configurationLocation) {
        CacheKey key = new CacheKey(configurationClass, configurationLocation);
        if (!key.equals(lastKey)) {
            lastKey = key;
            lastDatabase = new TestDatabase<>(configurationClass, configurationLocation);
        }
        return (TestDatabase<T>) lastDatabase;
    }

    private TestDatabase(Class<T> configurationClass, String configLocation) {
        try {
            databaseFixture = new DatabaseFixture<>(configurationClass, configLocation);
            cleanAndMigrate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void before() throws Throwable {
        cleanAndMigrate();
    }


    @SuppressWarnings("unused")
    public <DaoType> DaoType onDemand(final Class<DaoType> sqlObjectType) {
        return databaseFixture.getDatabase().onDemand(sqlObjectType);
    }

    @SuppressWarnings("unused")
    public <ReturnType> ReturnType withHandle(HandleCallback<ReturnType> callback) {
        return databaseFixture.getDatabase().withHandle(callback);
    }

    @SuppressWarnings("unused")
    public DBI getDBI() {
        return databaseFixture.getDatabase();
    }

    @SuppressWarnings("unused")
    public void cleanAndMigrate() throws Exception {
        databaseFixture.clean();
        databaseFixture.migrate();
    }

    private static class CacheKey {
        @SuppressWarnings("unused")
        private final Class configurationClass;
        @SuppressWarnings("unused")
        private final String configurationLocation;

        public CacheKey(Class configurationClass,
                        String configurationLocation) {
            this.configurationClass = configurationClass;
            this.configurationLocation = configurationLocation;
        }

        @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
        @Override
        public boolean equals(Object other) {
            return EqualsBuilder.reflectionEquals(this, other);
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }
    }

}
