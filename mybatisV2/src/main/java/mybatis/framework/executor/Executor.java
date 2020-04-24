package mybatis.framework.executor;

import mybatis.framework.config.Configuration;

import java.util.List;

public interface Executor {
    <T> List<T> query(String statementId, Object paramObject, Configuration configuration);
}
