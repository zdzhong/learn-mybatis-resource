package mybatis.framework.handler.parameter;

import mybatis.framework.config.MappedStatement;
import mybatis.framework.sqlsource.BoundSql;
import mybatis.framework.sqlsource.ParameterMapping;
import utils.SimpleClassUtil;

import java.lang.reflect.Field;
import java.sql.PreparedStatement;
import java.util.List;

public class DefaultParameterHandler implements ParameterHandler {

    private MappedStatement mappedStatement;
    private Object paramObject;
    private BoundSql boundSql;

    public DefaultParameterHandler(MappedStatement mappedStatement, Object paramObject, BoundSql boundSql) {
        this.mappedStatement = mappedStatement;
        this.paramObject = paramObject;
        this.boundSql = boundSql;
    }

    @Override
    public void setParameters(PreparedStatement statement) {
        List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
        if (null != parameterMappings) {
            for (int i = 0;i < parameterMappings.size();i++) {
                Class<?> type = paramObject.getClass();
                try {
                    if (SimpleClassUtil.isSimpleClass(type)) {
                        statement.setObject(1, paramObject);
                    } else {
                        // 列名
                        ParameterMapping param = parameterMappings.get(i);

                        // 根据列名获取入参对象的属性值，前提：列名和属性名称要一致
                        Field field = type.getDeclaredField(param.getName());
                        field.setAccessible(true);
                        // 获取属性值
                        Object value = field.get(paramObject);
                        statement.setObject(i + 1, value);
                    }
                } catch (Exception e){
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
