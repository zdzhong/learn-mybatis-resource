package mybatis.framework.config;

import mybatis.framework.executor.*;
import mybatis.framework.handler.parameter.DefaultParameterHandler;
import mybatis.framework.handler.parameter.ParameterHandler;
import mybatis.framework.handler.resultset.DefaultResultSetHandler;
import mybatis.framework.handler.resultset.ResultSetHandler;
import mybatis.framework.handler.statement.RoutingStatementHandler;
import mybatis.framework.handler.statement.StatementHandler;
import mybatis.framework.sqlsource.BoundSql;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class Configuration {

    private DataSource dataSource;

    private boolean cacheEnabled = true;

    private Map<String, MappedStatement> mappedStatements = new HashMap<>();

    protected ExecutorType defaultExecutorType = ExecutorType.SIMPLE;

    public void addMappedStatement(MappedStatement mappedStatement) {
        mappedStatements.put(mappedStatement.getStatementId(), mappedStatement);
    }

    public Executor newExecutor(ExecutorType executorType) {
        executorType = null == executorType ? ExecutorType.SIMPLE : executorType;
        Executor executor;
        if (ExecutorType.BATCH == executorType) {
            executor = new BatchExecutor(this);
        } else if (ExecutorType.REUSE == executorType) {
            executor = new ReuseExecutor(this);
        } else {
            executor = new SimpleExecutor(this);
        }
        if (cacheEnabled) {
            executor = new CachingExecutor(executor);
        }
        return executor;
    }

    public MappedStatement getMappedStatementById(String statementId) {
        return mappedStatements.get(statementId);
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
    }

    public void setDefaultExecutorType(ExecutorType defaultExecutorType) {
        this.defaultExecutorType = defaultExecutorType;
    }

    public ExecutorType getDefaultExecutorType() {
        return defaultExecutorType;
    }

    public StatementHandler newStatementHandler(MappedStatement mappedStatement, BoundSql boundSql, Configuration configuration, Object paramObject) {
        StatementHandler statementHandler = new RoutingStatementHandler(mappedStatement, boundSql, configuration, paramObject);
        return statementHandler;
    }

    public ParameterHandler newParameterHandler(MappedStatement mappedStatement, Object paramObject, BoundSql boundSql) {
        return new DefaultParameterHandler(mappedStatement, paramObject, boundSql);
    }

    public ResultSetHandler newResultSetHandler(MappedStatement mappedStatement, Object paramObject, BoundSql boundSql) {
        return new DefaultResultSetHandler(mappedStatement, paramObject, boundSql);
    }
}
