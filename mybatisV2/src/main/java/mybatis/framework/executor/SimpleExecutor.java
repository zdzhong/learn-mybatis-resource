package mybatis.framework.executor;

import mybatis.framework.config.Configuration;
import mybatis.framework.config.MappedStatement;
import mybatis.framework.handler.statement.StatementHandler;
import mybatis.framework.sqlsource.BoundSql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class SimpleExecutor extends BaseExecutor {

    public SimpleExecutor(Configuration configuration) {
        super(configuration.getDataSource());
    }

    @Override
    public <T> List<T> queryFromDataBase(MappedStatement mappedStatement, Configuration configuration, BoundSql boundSql, Object paramObject) {
        StatementHandler handler = configuration.newStatementHandler(mappedStatement, boundSql, configuration, paramObject);
        // 获取statement
        Statement statement = prepareStatement(handler);
        try {
            return handler.query(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Statement prepareStatement(StatementHandler handler) {
        Connection connection = null;
        try {
            connection = getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Statement statement = handler.prepare(connection);
        handler.parameterize(statement);
        return statement;
    }
}
