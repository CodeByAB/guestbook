package se.webstep.microservice.guestbook.resource;

import com.google.common.collect.ImmutableMap;
import com.sun.jersey.api.client.ClientResponse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import se.webstep.microservice.guestbook.MicroServicesApplication;
import se.webstep.microservice.guestbook.MicroServicesConfig;
import se.webstep.microservice.guestbook.jdbi.EntryDao;
import se.webstep.microservice.guestbook.util.ResourceTest;
import se.webstep.microservice.guestbook.util.TestDatabase;

import static io.dropwizard.testing.FixtureHelpers.fixture;
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

    @Before
    public void before() {
        when(service.getJdbi()).thenReturn(database.getDBI());
    }

    @Test
    public void post() {
        assertThat(database.getDBI().onDemand(EntryDao.class).list(10)).isEmpty();
        ClientResponse response = resourceTest.doPost("/guestbook/10/entry", ImmutableMap.of(
                "name", "Test",
                "email", "test@test.se",
                "message", "Test skriver"));
        assertThat(response.getStatus()).isEqualTo(201);
        assertThat(database.getDBI().onDemand(EntryDao.class).list(10)).isNotEmpty();
    }

    @Test
    public void get() throws Exception {
        ClientResponse createResponse = resourceTest.doPost("/guestbook/10/entry", ImmutableMap.of(
                "name", "Test",
                "email", "test@test.se",
                "message", "Test skriver"));
        assertThat(createResponse.getStatus()).isEqualTo(201);
        String location = createResponse.getHeaders().getFirst("Location");
        ClientResponse response = resourceTest.doGet(location);
        assertThat(response.getStatus()).isEqualTo(200);
        JSONAssert.assertEquals(fixture("fixture/getEntry.json"), response.getEntity(String.class), false);

    }


}
