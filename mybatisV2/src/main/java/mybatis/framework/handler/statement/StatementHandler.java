package mybatis.framework.handler.statement;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface StatementHandler {

    Statement prepare(Connection connection);

    <T> List<T> query(Statement statement) throws SQLException;

    void parameterize(Statement statement);
}
