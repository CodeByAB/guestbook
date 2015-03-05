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


    @SqlQuery("") // TODO
    public abstract ImmutableList<Entry> list(@Bind("id") long id);

    @SqlQuery("") // TODO
    @SingleValueResult
    public abstract Optional<Entry> get(@Bind("id") long id);


    public long save(long guestbookId, CreateEntry createEntry) {
        long id = getId();
        insert(id, createEntry.title, createEntry.message, guestbookId);
        return id;
    }


    @SqlUpdate("INSERT INTO entry (id, title, message, created_at, guestbook_id) VALUES (:id, :title, :message, sysdate, :guestbookId)")
    abstract void insert(@Bind("id") long id,
                         @Bind("title") String title,
                         @Bind("title") String message,
                         @Bind("guestbookId") long guestbookId
    );

    @SqlUpdate("") // TODO
    public abstract void delete(@Bind("id") long id);

    @SqlQuery("SELECT NEXT VALUE FOR entry_sequence")
    abstract long getId();


}
