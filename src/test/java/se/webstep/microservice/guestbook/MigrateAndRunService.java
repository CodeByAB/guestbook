package se.webstep.microservice.guestbook;

public class MigrateAndRunService {

    public static void main(String... args) throws Exception {
        MicroServicesApplication.main(new String[]{"db", "migrate", "src/test/resources/local-config.yml"});
        MicroServicesApplication.main(new String[]{"server", "src/test/resources/local-config.yml"});
    }


}
