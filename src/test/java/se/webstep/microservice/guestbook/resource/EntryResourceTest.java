package se.webstep.microservice.guestbook.resource;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.sun.jersey.api.client.ClientResponse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import se.webstep.microservice.guestbook.MicroServicesApplication;
import se.webstep.microservice.guestbook.MicroServicesConfig;
import se.webstep.microservice.guestbook.api.CreateGuestbook;
import se.webstep.microservice.guestbook.core.Guestbook;
import se.webstep.microservice.guestbook.jdbi.EntryDao;
import se.webstep.microservice.guestbook.jdbi.GuestbookDao;
import se.webstep.microservice.guestbook.util.ResourceTest;
import se.webstep.microservice.guestbook.util.TestDatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class EntryResourceTest {

    private MicroServicesApplication service = Mockito.mock(MicroServicesApplication.class);


    @Rule
    public TestDatabase<MicroServicesConfig> database =
            TestDatabase.forConfiguration(MicroServicesConfig.class,
                    "/local-test-config.yml");

    @Rule
    public ResourceTest resourceTest = new ResourceTest(new EntryResource(service));

    @Rule
    public ResourceTest entriesResourceTest = new ResourceTest(new EntriesResource(service));

    @Before
    public void before() {
        when(service.getJdbi()).thenReturn(database.getDBI());
    }

    @Test
    public void create() {
        database.getDBI().onDemand(GuestbookDao.class).save(new CreateGuestbook("kalle"));
        assertThat(database.getDBI().onDemand(EntryDao.class).list(3)).isEmpty();
        ClientResponse response = resourceTest.doPost("/guestbook/3/entry",
                ImmutableMap.of("text", "EntryText", "author", "nisse"));
        assertThat(response.getStatus()).isEqualTo(201);
        assertThat(database.getDBI().onDemand(EntryDao.class).list(3)).isNotEmpty();

        ClientResponse entriesResponse = entriesResourceTest.doGet("/guestbook/3/entries");
        assertThat(entriesResponse.getStatus()).isEqualTo(200);
    }

}
