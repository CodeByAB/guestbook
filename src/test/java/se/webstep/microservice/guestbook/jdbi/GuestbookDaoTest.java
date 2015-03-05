package se.webstep.microservice.guestbook.jdbi;

import org.assertj.core.api.Assertions;
import org.junit.Rule;
import org.junit.Test;
import se.webstep.microservice.guestbook.MicroServicesConfig;
import se.webstep.microservice.guestbook.api.CreateGuestbook;
import se.webstep.microservice.guestbook.util.TestDatabase;

public class GuestbookDaoTest {

    @Rule
    public TestDatabase<MicroServicesConfig> database =
            TestDatabase.forConfiguration(MicroServicesConfig.class,
                    "/local-test-config.yml");


    @Test
    public void save() {
        GuestbookDao guestbookDao = database.getDBI().onDemand(GuestbookDao.class);
        long id = guestbookDao.save(new CreateGuestbook("MyName"));
        Assertions.assertThat(guestbookDao.get(id).isPresent()).isTrue();
    }
}
