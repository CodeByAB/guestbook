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

    @SqlQuery("SELECT * FROM entry where id = :id")
    @SingleValueResult
    public abstract Optional<Entry> get(@Bind("id") long id);

    public long save(long guestbookId, CreateEntry createEntry){
        long id = getId();
        insert(id, guestbookId, createEntry.text, createEntry.author);
        return id;
    }

    @SqlUpdate("INSERT INTO entry (id, guestbook_id, text, author, created_at) VALUES (:id, :guestbookId, :text, :author, sysdate)")
    abstract void insert(@Bind("id") long id,
                         @Bind("guestbookId") long guestbookId,
                         @Bind("text") String text,
                         @Bind("author") String author);

    @SqlUpdate("DELETE FROM entry WHERE id = :id")
    public abstract void delete(@Bind("id") long id);


    @SqlQuery("SELECT NEXT VALUE FOR entry_sequence")
    abstract long getId();
}
