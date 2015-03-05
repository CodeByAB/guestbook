package se.webstep.microservice.guestbook.resource;

import com.google.common.collect.ImmutableMap;
import com.sun.jersey.api.client.ClientResponse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import se.webstep.microservice.guestbook.MicroServicesApplication;
import se.webstep.microservice.guestbook.MicroServicesConfig;
import se.webstep.microservice.guestbook.jdbi.GuestbookDao;
import se.webstep.microservice.guestbook.util.ResourceTest;
import se.webstep.microservice.guestbook.util.TestDatabase;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.when;

public class GuestbookResourceTest {

    private MicroServicesApplication service = Mockito.mock(MicroServicesApplication.class);


    @Rule
    public TestDatabase<MicroServicesConfig> database =
            TestDatabase.forConfiguration(MicroServicesConfig.class,
                    "/local-test-config.yml");

    @Rule
    public ResourceTest resourceTest = new ResourceTest(new GuestbookResource(service));


    @Before
    public void before() {
        when(service.getJdbi()).thenReturn(database.getDBI());
    }

    @Test
    public void create() {
        assertThat(database.getDBI().onDemand(GuestbookDao.class).list()).isEmpty();
        ClientResponse response = resourceTest.doPost("/guestbook", ImmutableMap.of("name", "Test"));
        assertThat(response.getStatus()).isEqualTo(201);
        assertThat(database.getDBI().onDemand(GuestbookDao.class).list()).isNotEmpty();
    }

    @Test
    public void listAll() {
        // Given
        String guestbookName = "Namnet på min gästbok!";

        // When
        resourceTest.doPost("/guestbook", ImmutableMap.of("name", guestbookName));

        // Then
        assertThat(resourceTest.doGet("/guestbook/list").getEntity(String.class)).contains(guestbookName);
    }

    @Test
    public void listOpen() {
        fail("TODO");

    }
}
