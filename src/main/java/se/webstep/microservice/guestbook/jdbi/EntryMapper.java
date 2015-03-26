package se.webstep.microservice.guestbook.jdbi;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import se.webstep.microservice.guestbook.core.Entry;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntryMapper implements ResultSetMapper<Entry> {
    @Override
    public Entry map(int i, ResultSet rs, StatementContext statementContext) throws SQLException {
        return new Entry(rs.getLong("id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("message"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                Entry.Status.valueOf(rs.getString("status")));
    }

}
