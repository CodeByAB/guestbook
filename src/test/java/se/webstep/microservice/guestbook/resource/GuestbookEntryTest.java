package se.webstep.microservice.guestbook.resource;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.sun.jersey.api.client.ClientResponse;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mockito;
import scala.util.parsing.combinator.testing.Str;
import se.webstep.microservice.guestbook.MicroServicesApplication;
import se.webstep.microservice.guestbook.MicroServicesConfig;
import se.webstep.microservice.guestbook.api.CreateEntry;
import se.webstep.microservice.guestbook.core.Entry;
import se.webstep.microservice.guestbook.jdbi.EntryDao;
import se.webstep.microservice.guestbook.jdbi.GuestbookDao;
import se.webstep.microservice.guestbook.util.ResourceTest;
import se.webstep.microservice.guestbook.util.TestDatabase;
import sun.jvm.hotspot.utilities.Assert;

import java.net.URI;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

public class GuestbookEntryTest {
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
    public void shouldCreateEntry() {
        String expectedMessage = "Test av inlägg.";
        int guestbookId = 3;

        ClientResponse response = resourceTest.doPost(String.format("/guestbook/%s/entry", guestbookId), ImmutableMap.of("message", expectedMessage));

        assertThat(response.getStatus()).isEqualTo(201);

        EntryDao entryDao = database.getDBI().onDemand(EntryDao.class);
        ImmutableList<Entry> list = entryDao.list(guestbookId);
        assertThat(list)
                .isNotEmpty()
                .hasSize(1)
                .extracting("message").isEqualTo(Arrays.asList(expectedMessage));

    }

    @Test
    public void shouldDeleteEntry() {
        long guestBookId = 1;

        EntryDao entryDao = database.getDBI().onDemand(EntryDao.class);

        CreateEntry e1 = new CreateEntry("entry 1");
        CreateEntry e2 = new CreateEntry("entry 2");

        long entryToDelete = entryDao.save(guestBookId, e1);
        entryDao.save(guestBookId, e2);

        ClientResponse response = resourceTest.doDelete(String.format("/guestbook/%s/entry", entryToDelete));
        assertThat(response.getStatus()).isEqualTo(200);

        ImmutableList<Entry> list = entryDao.list(guestBookId);
        assertThat(list)
                .isNotEmpty()
                .hasSize(1);

        assertThat(list.get(0).id).isNotEqualTo(entryToDelete);
    }
}
