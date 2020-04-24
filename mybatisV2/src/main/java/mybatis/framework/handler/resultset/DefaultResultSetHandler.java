package mybatis.framework.handler.resultset;

import mybatis.framework.config.MappedStatement;
import mybatis.framework.sqlsource.BoundSql;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class DefaultResultSetHandler implements ResultSetHandler {

    private MappedStatement mappedStatement;
    private Object paramObject;
    private BoundSql boundSql;

    public DefaultResultSetHandler(MappedStatement mappedStatement, Object paramObject, BoundSql boundSql) {
        this.mappedStatement = mappedStatement;
        this.paramObject = paramObject;
        this.boundSql = boundSql;
    }

    @Override
    public <T> List<T> handleCursorResultSets(PreparedStatement ps) {
        try {
            ResultSet resultSet = ps.getResultSet();
            if (null == resultSet) {
                return null;
            }
            List<T> results = new ArrayList<>();
            Class<?> resultTypeClass = mappedStatement.getResultTypeClass();
            Object result = null;
            while (resultSet.next()) {
                result = resultTypeClass.newInstance();
                ResultSetMetaData metaData = resultSet.getMetaData();
                int columnCount = metaData.getColumnCount();
                for (int i = 0; i < columnCount; i++) {
                    String columnName = metaData.getColumnName(i + 1);
                    Field field = resultTypeClass.getDeclaredField(columnName);
                    field.setAccessible(true);
                    field.set(result, resultSet.getObject(columnName));
                }
                results.add((T)result);
            }
            return results;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
