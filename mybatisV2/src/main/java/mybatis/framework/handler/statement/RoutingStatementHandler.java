package mybatis.framework.handler.statement;

import mybatis.framework.config.Configuration;
import mybatis.framework.config.MappedStatement;
import mybatis.framework.sqlsource.BoundSql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class RoutingStatementHandler implements StatementHandler {
    private StatementHandler delegate;

    public RoutingStatementHandler(MappedStatement mappedStatement, BoundSql boundSql, Configuration configuration, Object paramObject) {
        switch (mappedStatement.getStatementType()) {
            case "statement":
                delegate = new SimpleStatementHandler(mappedStatement, boundSql, configuration, paramObject);
                break;
            case "prepared":
                delegate = new PreparedStatementHandler(mappedStatement, boundSql, configuration, paramObject);
                break;
            case "callable":
                delegate = new CallableStatementHandler(mappedStatement, boundSql, configuration, paramObject);
                break;
            default:
                throw new RuntimeException("Unknown statement type: " + mappedStatement.getStatementType());
        }
    }

    @Override
    public Statement prepare(Connection connection) {
        return delegate.prepare(connection);
    }

    @Override
    public <T> List<T> query(Statement statement) throws SQLException {
        return delegate.query(statement);
    }

    @Override
    public void parameterize(Statement statement) {
        delegate.parameterize(statement);
    }
}
