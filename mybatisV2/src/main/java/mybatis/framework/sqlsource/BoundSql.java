package mybatis.framework.sqlsource;

import java.util.ArrayList;
import java.util.List;

public class BoundSql {
    private String sql;

    private List<ParameterMapping> parameterMappings = new ArrayList<ParameterMapping>();

    public BoundSql(String sql, List<ParameterMapping> parameterMappings) {
        this.sql = sql;
        this.parameterMappings = parameterMappings;
    }

    public String getSql() {
        return sql;
    }

    public List<ParameterMapping> getParameterMappings() {
        return parameterMappings;
    }
}
