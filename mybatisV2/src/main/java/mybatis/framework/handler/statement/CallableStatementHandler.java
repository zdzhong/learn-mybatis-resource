package mybatis.framework.handler.statement;

import mybatis.framework.config.Configuration;
import mybatis.framework.config.MappedStatement;
import mybatis.framework.sqlsource.BoundSql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class CallableStatementHandler extends BaseStatementHandler {
    public CallableStatementHandler(MappedStatement mappedStatement, BoundSql boundSql, Configuration configuration, Object paramObject) {
        super(mappedStatement, boundSql, configuration, paramObject);
    }

    @Override
    protected Statement instantiateStatement(Connection connection) {
        return null;
    }

    @Override
    public <T> List<T> query(Statement statement) throws SQLException {
        return null;
    }

    @Override
    public void parameterize(Statement statement) {

    }
}
