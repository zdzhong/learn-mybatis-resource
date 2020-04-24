package mybatis.framework.executor;

import mybatis.framework.config.Configuration;

import java.util.List;

public class CachingExecutor implements Executor {
    private Executor delegate;

    public CachingExecutor(Executor executor) {
        this.delegate = executor;
    }

    @Override
    public <T> List<T> query(String statementId, Object paramObject, Configuration configuration) {
        // 先从二级缓存中取数据

        // 如果二级缓存中没有获取到，委托其它executor进行处理
        return delegate.query(statementId, paramObject, configuration);
    }
}
