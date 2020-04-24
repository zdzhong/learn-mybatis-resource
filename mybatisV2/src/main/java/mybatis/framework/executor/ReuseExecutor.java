package mybatis.framework.executor;

import mybatis.framework.config.Configuration;
import mybatis.framework.config.MappedStatement;
import mybatis.framework.sqlsource.BoundSql;

import java.util.List;

public class ReuseExecutor extends BaseExecutor {
    public ReuseExecutor(Configuration configuration) {
        super(configuration.getDataSource());
    }

    @Override
    public <T> List<T> queryFromDataBase(MappedStatement mappedStatement, Configuration configuration, BoundSql boundSql, Object param) {
        return null;
    }
}
