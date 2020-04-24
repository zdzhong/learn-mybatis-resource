package mybatis.framework.handler.statement;

import mybatis.framework.config.Configuration;
import mybatis.framework.config.MappedStatement;
import mybatis.framework.handler.statement.BaseStatementHandler;
import mybatis.framework.sqlsource.BoundSql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public class PreparedStatementHandler extends BaseStatementHandler {

    public PreparedStatementHandler(MappedStatement mappedStatement, BoundSql boundSql, Configuration configuration, Object paramObject) {
        super(mappedStatement, boundSql, configuration, paramObject);
    }

    @Override
    protected Statement instantiateStatement(Connection connection) throws SQLException {
        String sql = boundSql.getSql();
        if (mappedStatement.getResultSetType().equals("default")) {
            return connection.prepareStatement(sql);
        }
        return null;
    }

    @Override
    public <T> List<T> query(Statement statement) throws SQLException {
        PreparedStatement ps = (PreparedStatement) statement;
        ps.execute();
        return resultSetHandler.handleCursorResultSets(ps);
    }

    @Override
    public void parameterize(Statement statement) {
        parameterHandler.setParameters((PreparedStatement) statement);
    }
}
