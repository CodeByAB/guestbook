package se.webstep.microservice.guestbook.jdbi;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.Transaction;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult;
import se.webstep.microservice.guestbook.api.CreateEntry;
import se.webstep.microservice.guestbook.core.Entry;
import se.webstep.microservice.guestbook.core.Entry.Status;
import se.webstep.microservice.guestbook.util.BindEnum;
import se.webstep.microservice.guestbook.util.BindFields;

@RegisterMapper(EntryMapper.class)
public abstract class EntryDao {

    @SqlQuery("SELECT * FROM entry WHERE fk_guestbook_id = :guestbookId")
    public abstract ImmutableList<Entry> list(@Bind("guestbookId") long id);

    @SqlQuery("SELECT * from entry WHERE id = :id")
    @SingleValueResult
    public abstract Optional<Entry> get(@Bind("id") long id);

    @Transaction
    public long save(long guestbookId, CreateEntry createEntry) {
        long id = getId();
        insert(id, createEntry, Status.UN_READABLE, guestbookId);
        return id;
    }

    @SqlUpdate("DELETE FROM entry WHERE id = :id")
    public abstract void delete(@Bind("id") long id);

    @SqlUpdate("UPDATE entry SET status = :status WHERE id = :id")
    public abstract void updateStatus(@Bind("id") long id, @BindEnum("status") Status status);

    @SqlUpdate("INSERT INTO entry (id, name, email, message, status, fk_guestbook_id, created_at) " +
            "VALUES (:id, :f.name, :f.email, :f.message, :status, :guestbookId, sysdate)")
    abstract void insert(@Bind("id") long id,
                         @BindFields("f") CreateEntry entry,
                         @BindEnum("status") Status status,
                         @Bind("guestbookId") long guestbookId);

    @SqlQuery("SELECT NEXT VALUE FOR entry_sequence")
    abstract long getId();
}
