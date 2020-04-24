package mybatis.framework.sqlsource;

import mybatis.framework.sqlnode.DynamicContext;
import mybatis.framework.sqlnode.SqlNode;

public class RawSqlSource implements SqlSource {
    private SqlSource sqlSource;

    public RawSqlSource(SqlNode rootSqlNode) {
        DynamicContext context = new DynamicContext(null);
        rootSqlNode.apply(context);
        SqlSourceParser parser = new SqlSourceParser();
        this.sqlSource = parser.parse(context.getSql());
    }

    @Override
    public BoundSql getBoundSql(Object paramObject) {
        return sqlSource.getBoundSql(paramObject);
    }
}
