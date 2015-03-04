package se.webstep.services.guestbook;

public class RunService {

    public static void main(String... args) throws Exception {
        GuestBookServicesApplication.main(new String[]{"db","migrate", "src/test/resource/local-config.yml"});
        GuestBookServicesApplication.main(new String[]{"server", "src/test/resource/local-config.yml"});
    }


}
