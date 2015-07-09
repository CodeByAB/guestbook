package se.webstep.microservice.guestbook.resource;

import com.google.common.collect.ImmutableMap;
import org.glassfish.jersey.client.ClientResponse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import se.webstep.microservice.guestbook.MicroServicesApplication;
import se.webstep.microservice.guestbook.MicroServicesConfig;
import se.webstep.microservice.guestbook.jdbi.GuestbookDao;
import se.webstep.microservice.guestbook.util.ResourceTest;
import se.webstep.microservice.guestbook.util.TestDatabase;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;
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
    public void create() throws Exception {
        assertThat(database.getDBI().onDemand(GuestbookDao.class).list()).isEmpty();

        Response response = resourceTest.doPost("/guestbook", Entity.entity(ImmutableMap.of("name", "Test"), MediaType.APPLICATION_JSON_TYPE));
        assertThat(response.getStatus()).isEqualTo(201);
        assertThat(database.getDBI().onDemand(GuestbookDao.class).list()).isNotEmpty();


        //JSONAssert.assertEquals(FixtureHelpers.fixture("fixtures/test.json"), response.readEntity(String.class), false);
    }

}
