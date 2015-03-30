package se.webstep.microservice.guestbook.resource;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.sun.jersey.api.client.ClientResponse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import org.skyscreamer.jsonassert.JSONAssert;
import se.webstep.microservice.guestbook.MicroServicesApplication;
import se.webstep.microservice.guestbook.MicroServicesConfig;
import se.webstep.microservice.guestbook.api.CreateEntry;
import se.webstep.microservice.guestbook.core.Entry;
import se.webstep.microservice.guestbook.jdbi.EntryDao;
import se.webstep.microservice.guestbook.util.ResourceTest;
import se.webstep.microservice.guestbook.util.TestDatabase;

import java.util.ArrayList;

import static io.dropwizard.testing.FixtureHelpers.fixture;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class EntryResourceTest {

    private MicroServicesApplication service = Mockito.mock(MicroServicesApplication.class);
    public static long GUESTBOOK_ID = 100;

    @Rule
    public TestDatabase<MicroServicesConfig> database =
            TestDatabase.forConfiguration(MicroServicesConfig.class,
                    "/local-test-config.yml");

    @Rule
    public ResourceTest resourceTest = new ResourceTest(new EntryResource(service), new EntriesResource(service));

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

    @Test
    public void getUnknownEntry() throws Exception {
        ClientResponse response = resourceTest.doGet("/guestbook/10/entry/1");
        assertThat(response.getStatus()).isEqualTo(404);
    }

    @Test
    public void delete() {
        EntryDao entryDao = database.getDBI().onDemand(EntryDao.class);
        long id = entryDao.save(10, new CreateEntry("Name", "name@name.se", "Message"));

        assertThat(entryDao.get(id).isPresent());

        ClientResponse response = resourceTest.doDelete(String.format("/guestbook/10/entry/%d", id));
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(!entryDao.get(id).isPresent());
    }

    @Test
    public void setReadable() {
        EntryDao entryDao = database.getDBI().onDemand(EntryDao.class);
        long id = entryDao.save(10, new CreateEntry("Name", "name@name.se", "Message"));

        assertThat(entryDao.get(id).isPresent());
        assertThat(entryDao.get(id).get().status).isEqualTo(Entry.Status.UN_READABLE);

        ClientResponse response = resourceTest.doPut(String.format("/guestbook/10/entry/%d/readable", id), null);
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(entryDao.get(id).get().status).isEqualTo(Entry.Status.READABLE);
    }

    @Test
    public void setUnReadable() {
        EntryDao entryDao = database.getDBI().onDemand(EntryDao.class);
        long id = entryDao.save(10, new CreateEntry("Name", "name@name.se", "Message"));

        assertThat(entryDao.get(id).isPresent());
        entryDao.updateStatus(id, Entry.Status.READABLE);
        assertThat(entryDao.get(id).get().status).isEqualTo(Entry.Status.READABLE);

        ClientResponse response = resourceTest.doPut(String.format("/guestbook/10/entry/%d/un_readable", id), null);
        assertThat(response.getStatus()).isEqualTo(200);
        assertThat(entryDao.get(id).get().status).isEqualTo(Entry.Status.UN_READABLE);
    }


    @Test
    public void getReadable() throws Exception {
        createEntry(5, Entry.Status.READABLE);
        createEntry(3, Entry.Status.UN_READABLE);

        ClientResponse response = resourceTest.doGet("/guestbook/100/entries/readable");

        assertThat(response.getStatus()).isEqualTo(200);
        JSONAssert.assertEquals(fixture("fixture/getReadable.json"), response.getEntity(String.class), false);
    }

    @Test
    public void getUnReadable() throws Exception {
        createEntry(5, Entry.Status.READABLE);
        createEntry(3, Entry.Status.UN_READABLE);

        ClientResponse response = resourceTest.doGet("/guestbook/100/entries/un_readable");

        assertThat(response.getStatus()).isEqualTo(200);
        JSONAssert.assertEquals(fixture("fixture/getUnReadable.json"), response.getEntity(String.class), false);
    }

    @Test
    public void getAll() throws Exception {
        createEntry(2, Entry.Status.READABLE);
        createEntry(2, Entry.Status.UN_READABLE);

        ClientResponse response = resourceTest.doGet("/guestbook/100/entries");

        assertThat(response.getStatus()).isEqualTo(200);
        JSONAssert.assertEquals(fixture("fixture/getAll.json"), response.getEntity(String.class), false);
    }


    private ImmutableList<Long> createEntry(int numberOfEntries, Entry.Status status) {
        ArrayList<Long> ids = new ArrayList<>(numberOfEntries);
        EntryDao entryDao = database.getDBI().onDemand(EntryDao.class);
        for (int i = 0; i < numberOfEntries; i++) {
            String name = "Name_" + i;
            long id = entryDao.
                    save(GUESTBOOK_ID, new CreateEntry(name, name + "@test.se", name + " testar gästboken"));
            ids.add(id);
            entryDao.updateStatus(id, status);

        }
        return ImmutableList.copyOf(ids);
    }


}
