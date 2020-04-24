package mybatis.framework.sqlnode;

import java.util.HashMap;
import java.util.Map;

public class DynamicContext {
    private StringBuffer stringBuffer = new StringBuffer();

    private Map<String, Object> bindings = new HashMap<>();

    public DynamicContext(Object paramObject){
        bindings.put("_parameter", paramObject);
    }

    public void appendSql(String sqlText) {
        stringBuffer.append(sqlText);
        stringBuffer.append(" ");
    }

    public String getSql() {
        return stringBuffer.toString();
    }

    public Map<String, Object> getBindings() {
        return bindings;
    }
}
