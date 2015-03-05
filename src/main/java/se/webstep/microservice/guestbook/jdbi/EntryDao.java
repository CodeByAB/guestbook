package se.webstep.microservice.guestbook.jdbi;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.GetGeneratedKeys;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult;
import se.webstep.microservice.guestbook.api.CreateEntry;
import se.webstep.microservice.guestbook.core.Entry;

import java.time.LocalDateTime;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;

@RegisterMapper(EntryMapper.class)
public abstract class EntryDao {


    @SqlQuery("Select * from Entry where id =  :id")
    public abstract ImmutableList<Entry> list(@Bind("id") long id);

    @SqlQuery("Select * from Entry where guestbook_id =  :id order by id desc")
    public abstract ImmutableList<Entry> listbyGuestbook(@Bind("id") long id);


    @SqlQuery("select * form Entry where id = :id")
    @SingleValueResult
    public abstract Optional<Entry> get(@Bind("id") long id);

    public long save(CreateEntry createEntry) {
        long id = getId();


        save(id,createEntry.guestbook_id, createEntry.name, createEntry.message);
        return id;
    }

    @SqlUpdate("insert into Entry (id, guestbook_id, name,message) values (:id, :guestbook_id, :name, :message)")
    public abstract void save(@Bind("id") long id,
                              @Bind("guestbook_id") long guestbook_id,
                              @Bind("name") String name,
                              @Bind("message") String message);




    @SqlUpdate("Delete from Entry where id = :id")
    public abstract void delete(@Bind("id") long id);

    @SqlQuery("SELECT NEXT VALUE FOR guestbook_sequence")
    abstract long getId();
}
