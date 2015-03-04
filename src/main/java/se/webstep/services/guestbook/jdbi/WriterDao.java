package se.webstep.services.guestbook.jdbi;


import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import se.webstep.services.guestbook.api.WriterCreator;
import se.webstep.services.guestbook.core.Writer;

@RegisterMapper(WriterMapper.class)
public abstract class WriterDao {

    public Writer insert(WriterCreator writerCreator) {
        final long id = getId();
        insert(id, writerCreator.name, writerCreator.email);
        return new Writer(id, writerCreator.name, writerCreator.email);
    }

    @SqlQuery("select * from writer where id = :id")
    public abstract Writer get(@Bind("id") long id);

    @SqlUpdate("insert into writer values(:id, :name, :email)")
    abstract int insert(@Bind("id") long id, @Bind("name") String name, @Bind("email") String email);

    @SqlQuery("select writer_sequence.NEXTVAL from dual")
    abstract long getId();



}
