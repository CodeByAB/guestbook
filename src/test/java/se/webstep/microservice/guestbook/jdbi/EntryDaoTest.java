package se.webstep.microservice.guestbook.jdbi;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.junit.Rule;
import org.junit.Test;
import se.webstep.microservice.guestbook.MicroServicesConfig;
import se.webstep.microservice.guestbook.api.CreateEntry;
import se.webstep.microservice.guestbook.core.Entry;
import se.webstep.microservice.guestbook.util.TestDatabase;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class EntryDaoTest {

    public static long GUESTBOOK_ID = 100;

    @Rule
    public TestDatabase<MicroServicesConfig> database =
            TestDatabase.forConfiguration(MicroServicesConfig.class,
                    "/local-test-config.yml");

    private EntryDao entryDao = database.getDBI().onDemand(EntryDao.class);

    @Test
    public void insert() {
        ImmutableList<Long> ids = createEntry(1);
        assertThat(ids).isNotEmpty();
    }

    @Test
    public void get() {
        ImmutableList<Long> ids = createEntry(3);

        for (Long id : ids) {
            Optional<Entry> optional = entryDao.get(id);
            assertThat(optional.isPresent()).isTrue();
            assertThat(optional.get().id).isEqualTo(id);
        }
    }

    @Test
    public void list() {
        createEntry(11);
        assertThat(entryDao.list(GUESTBOOK_ID)).hasSize(11);
    }

    @Test
    public void delete() {
        ImmutableList<Long> ids = createEntry(1);
        assertThat(entryDao.get(ids.get(0)).isPresent()).isTrue();

        entryDao.delete(ids.get(0));
        assertThat(entryDao.get(ids.get(0)).isPresent()).isFalse();
    }

    private ImmutableList<Long> createEntry(int numberOfEntries) {
        ArrayList<Long> ids = new ArrayList<>(numberOfEntries);
        for (int i = 0; i < numberOfEntries; i++) {
            String name = "Name_" + i;
            ids.add(entryDao.save(GUESTBOOK_ID, new CreateEntry(name, name + "@test.se", name + " testar gästboken")));
        }
        return ImmutableList.copyOf(ids);
    }


}
