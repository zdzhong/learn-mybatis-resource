package mybatis.framework.sqlsource;

import mybatis.framework.sqlnode.DynamicContext;
import mybatis.framework.sqlnode.SqlNode;

public class DynamicSqlSource implements SqlSource {
    private SqlNode rootSqlNode;

    public DynamicSqlSource(SqlNode rootSqlNode) {
        this.rootSqlNode = rootSqlNode;
    }

    @Override
    public BoundSql getBoundSql(Object paramObject) {
        DynamicContext context = new DynamicContext(paramObject);
        rootSqlNode.apply(context);
        System.out.println(context.getSql());
        SqlSourceParser sqlSourceParser = new SqlSourceParser();
        SqlSource sqlSource = sqlSourceParser.parse(context.getSql());
        return sqlSource.getBoundSql(paramObject);
    }
}
