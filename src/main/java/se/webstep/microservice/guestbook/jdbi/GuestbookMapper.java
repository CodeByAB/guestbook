package se.webstep.microservice.guestbook.jdbi;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import se.webstep.microservice.guestbook.core.Guestbook;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GuestbookMapper implements ResultSetMapper<Guestbook> {
    @Override
    public Guestbook map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Guestbook(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                Guestbook.Type.valueOf(resultSet.getString("status")),
                resultSet.getTimestamp("created_at").toLocalDateTime());
    }
}
