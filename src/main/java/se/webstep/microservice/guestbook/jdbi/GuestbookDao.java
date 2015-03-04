package se.webstep.microservice.guestbook.jdbi;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult;
import se.webstep.microservice.guestbook.api.CreateGuestbook;
import se.webstep.microservice.guestbook.core.Guestbook;


@RegisterMapper(GuestbookMapper.class)
public abstract class GuestbookDao {

    @SqlQuery("SELECT * FROM guestbook WHERE id = :id")
    @SingleValueResult
    public abstract Optional<Guestbook> get(@Bind("id") long id);

    @SqlQuery("SELECT * FROM guestbook")
    public abstract ImmutableList<Guestbook> list();

    public long save(CreateGuestbook createGuestbook) {
        long id = getId();
        insert(id, createGuestbook.name, createGuestbook.status.name());
        return id;
    }

    @SqlUpdate("INSERT INTO guestbook (id, name, status, created_at) VALUES (:id, :name, :status, sysdate)")
    abstract void insert(@Bind("id") long id, @Bind("name") String name, @Bind("status") String status);

    @SqlUpdate("DELETE FROM guestbook WHERE id = :id")
    public abstract void delete(@Bind("id") long id);

    @SqlQuery("SELECT NEXT VALUE FOR guestbook_sequence")
    abstract long getId();

}
