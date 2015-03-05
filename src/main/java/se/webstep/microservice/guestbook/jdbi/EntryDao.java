package se.webstep.microservice.guestbook.jdbi;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import io.dropwizard.jersey.params.LongParam;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult;
import se.webstep.microservice.guestbook.api.CreateEntry;
import se.webstep.microservice.guestbook.core.Entry;

@RegisterMapper(EntryMapper.class)
public abstract class EntryDao {


    @SqlQuery("SELECT * FROM entry WHERE guestbook_id = :id")
    public abstract ImmutableList<Entry> list(@Bind("id") long id);

    @SqlQuery("SELECT * FROM entry WHERE id = :id")
    @SingleValueResult
    public abstract Optional<Entry> get(@Bind("id") long id);

    public long save(long guestbookId, CreateEntry entry) {
        long id = nextId();
        save(id, guestbookId, entry.message);
        return id;
    }

    @SqlQuery("SELECT NEXT VALUE FOR entry_sequence")
    protected abstract long nextId();

    @SqlUpdate("INSERT INTO entry (id, guestbook_id, message) VALUES (:id, :guestbook_id, :message)")
    abstract void save(@Bind("id") long id, @Bind("guestbook_id") long guestbookId, @Bind("message") String message);

    @SqlUpdate("DELETE FROM entry WHERE id = :id")
    public abstract void delete(@Bind("id") long id);
}
