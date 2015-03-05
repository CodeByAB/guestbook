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

    @SqlQuery("SELECT * FROM entry WHERE guestbook_id = :id")
    public abstract ImmutableList<Entry> list(@Bind("id") long id);

    @SqlQuery("SELECT * FROM entry WHERE guestbook_id = :guestbook_id AND id = :id") // TODO
    @SingleValueResult
    public abstract Optional<Entry> get( @Bind("guestbook_id") long guestbookId, @Bind("id") long id);

    public long save(long guestbookId, CreateEntry createEntry) {
        long id = getId();
        insert(id, guestbookId, createEntry.text, createEntry.author);
        return id;
    }

    @SqlUpdate("INSERT INTO entry(id, guestbook_id, text, author) VALUES(:id, :guestbook_id, :text, :author)")
    abstract void insert(@Bind("id") long id,
                         @Bind("guestbook_id") long guestbook_id,
                         @Bind("text") String text,
                         @Bind("author") String author);

    @SqlUpdate("DELETE FROM entry where id = :id")
    public abstract void delete(@Bind("id") long id);

    @SqlQuery("SELECT NEXT VALUE FOR entry_sequence")
    abstract long getId();
}
