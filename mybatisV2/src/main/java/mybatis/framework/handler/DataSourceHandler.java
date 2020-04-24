package mybatis.framework.handler;

import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class DataSourceHandler {
    private Map<String, DataSource> dataSourceMap = new HashMap<>(8);

    public DataSourceHandler() {
        // dbcp数据连接池
        dataSourceMap.put("dbcp", new BasicDataSource());
    }

    public DataSource getDataSource(String type) {
        return dataSourceMap.get(type);
    }
}
