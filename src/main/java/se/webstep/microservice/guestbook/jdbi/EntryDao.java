package se.webstep.microservice.guestbook.jdbi;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.customizers.SingleValueResult;
import se.webstep.microservice.guestbook.core.Entry;

@RegisterMapper(EntryMapper.class)
public abstract class EntryDao {


    @SqlQuery("") // TODO
    public abstract ImmutableList<Entry> list(@Bind("id") long id);

    @SqlQuery("") // TODO
    @SingleValueResult
    public abstract Optional<Entry> get(@Bind("id") long id);

    @SqlUpdate // TODO
    public abstract void save();

    @SqlUpdate("") // TODO
    public abstract void delete(@Bind("id") long id);
}
