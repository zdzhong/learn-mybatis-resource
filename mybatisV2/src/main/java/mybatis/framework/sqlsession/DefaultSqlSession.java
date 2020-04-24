package mybatis.framework.sqlsession;

import mybatis.framework.config.Configuration;
import mybatis.framework.executor.Executor;

import java.util.List;

public class DefaultSqlSession implements SqlSession {

    private Configuration configuration;

    private Executor executor;

    public DefaultSqlSession(Configuration configuration, Executor executor) {
        this.configuration = configuration;
        this.executor = executor;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T selectOne(String statementId, Object paramObject) {
        List<Object> list = this.selectList(statementId, paramObject);
        if (null != list && 1 == list.size()) {
            return (T) list.get(0);
        }
        return null;
    }

    private <T> List<T> selectList(String statementId, Object paramObject) {
        return executor.query(statementId, paramObject, configuration);
    }
}
