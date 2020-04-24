package mybatis.framework.sqlsource;

import java.util.ArrayList;
import java.util.List;

public class StaticSqlSource implements SqlSource {
    private String sql;
    private List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();

    public StaticSqlSource(String sql, List<ParameterMapping> parameterMappings){
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }


    @Override
    public BoundSql getBoundSql(Object paramObject) {
        return new BoundSql(sql, parameterMappings);
    }
}
