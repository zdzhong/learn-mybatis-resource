package mybatis.framework.sqlsource;

public interface SqlSource {

    BoundSql getBoundSql(Object paramObject);
}
