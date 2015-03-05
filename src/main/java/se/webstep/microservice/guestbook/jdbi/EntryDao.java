package se.webstep.microservice.guestbook.jdbi;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult;
import se.webstep.microservice.guestbook.api.CreateEntry;
import se.webstep.microservice.guestbook.core.Entry;

@RegisterMapper(EntryMapper.class)
public abstract class EntryDao {


    @SqlQuery("SELECT * FROM entry WHERE guestbook_id = :guestbookId")
    public abstract ImmutableList<Entry> list(@Bind("guestbookId") long guestbookId);

    @SqlQuery("SELECT * FROM entry WHERE id = :id AND guestbook_id = :guestbookId")
    @SingleValueResult
    public abstract Optional<Entry> get(@Bind("guestbookId") long guestbookId, @Bind("id") long id);

//    @SqlUpdate("INSERT INTO entry (id, message created_at) VALUES (:id, :message, sysdate)")
//    public abstract void save(CreateEntry createEntry);

    public long save(Long guestbookId, CreateEntry createEntry) {
        long id = getId();
        insert(id, guestbookId, createEntry.message);
        return id;
    }

    @SqlUpdate("INSERT INTO entry (id, guestbook_id, message, created_at) VALUES (:id, :guestbookId, :message, sysdate)")
    abstract void insert(@Bind("id") long id,
                         @Bind("guestbookId") long guestbookId,
                         @Bind("message") String message);

    @SqlUpdate("DELETE FROM entry WHERE id = :id")
    public abstract void delete(@Bind("id") long id);

    @SqlQuery("SELECT NEXT VALUE FOR entry_sequence")
    abstract long getId();
}
