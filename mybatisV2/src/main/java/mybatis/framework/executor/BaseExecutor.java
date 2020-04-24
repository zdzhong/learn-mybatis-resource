package mybatis.framework.executor;

import mybatis.framework.config.Configuration;
import mybatis.framework.config.MappedStatement;
import mybatis.framework.sqlsource.BoundSql;
import mybatis.framework.sqlsource.SqlSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseExecutor implements Executor {

    private Map<String, List<Object>> oneLevelCache = new HashMap<>();

    private DataSource dataSource;

    public BaseExecutor(DataSource dataSource){
        this.dataSource = dataSource;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> List<T> query(String statementId, Object paramObject, Configuration configuration) {
        MappedStatement mappedStatement = configuration.getMappedStatementById(statementId);
        SqlSource sqlSource = mappedStatement.getSqlSource();
        BoundSql boundSql = sqlSource.getBoundSql(paramObject);
        // 先从一级缓存中取

        // 如果一级缓存中没有，则委托子类走数据库查询
        List<T> list = queryFromDataBase(mappedStatement, configuration, boundSql, paramObject);
        return (List<T>) list;
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public abstract <T> List<T> queryFromDataBase(MappedStatement mappedStatement, Configuration configuration, BoundSql boundSql, Object param);

}
