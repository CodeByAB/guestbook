package se.webstep.services.guestbook.jdbi;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import se.webstep.services.guestbook.core.Writer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WriterMapper implements ResultSetMapper<Writer> {
    @Override
    public Writer map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        return new Writer(resultSet.getLong("id"), resultSet.getString("name"), resultSet.getString("email"));
    }
}
