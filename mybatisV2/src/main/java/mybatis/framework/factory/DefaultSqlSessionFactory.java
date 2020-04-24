package mybatis.framework.factory;

import mybatis.framework.config.Configuration;
import mybatis.framework.executor.Executor;
import mybatis.framework.sqlsession.DefaultSqlSession;
import mybatis.framework.sqlsession.SqlSession;

public class DefaultSqlSessionFactory implements SqlSessionFactory {

    private Configuration configuration;

    public DefaultSqlSessionFactory(Configuration configuration){
        this.configuration = configuration;
    }

    @Override
    public SqlSession openSession() {
        Executor executor = configuration.newExecutor(configuration.getDefaultExecutorType());
        return new DefaultSqlSession(configuration, executor);
    }
}
