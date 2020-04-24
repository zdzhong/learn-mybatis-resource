package mybatis.framework.handler.statement;

import mybatis.framework.config.Configuration;
import mybatis.framework.config.MappedStatement;
import mybatis.framework.handler.parameter.ParameterHandler;
import mybatis.framework.handler.resultset.ResultSetHandler;
import mybatis.framework.sqlsource.BoundSql;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public abstract class BaseStatementHandler implements StatementHandler {

    protected MappedStatement mappedStatement;

    protected BoundSql boundSql;

    protected ParameterHandler parameterHandler;

    protected ResultSetHandler resultSetHandler;

    public BaseStatementHandler(MappedStatement mappedStatement, BoundSql boundSql, Configuration configuration, Object paramObject) {
        this.mappedStatement = mappedStatement;
        this.boundSql = boundSql;

        this.parameterHandler = configuration.newParameterHandler(mappedStatement, paramObject, boundSql);
        this.resultSetHandler = configuration.newResultSetHandler(mappedStatement, paramObject, boundSql);
    }

    @Override
    public Statement prepare(Connection connection) {
        // 具体创建什么类型的statement交由子类进行创建
        Statement statement = null;
        try {
            statement = instantiateStatement(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // 父类可以做一些通用的事情
        // 比如设置超时时长，最大查询数
//         setStatementTimeout(statement, transactionTimeout);
         setFetchSize(statement);
        return statement;
    }

    private void setFetchSize(Statement statement) {

    }

    protected abstract Statement instantiateStatement(Connection connection) throws SQLException;
}
