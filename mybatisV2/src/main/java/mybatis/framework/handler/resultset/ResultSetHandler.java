package mybatis.framework.handler.resultset;

import java.sql.PreparedStatement;
import java.util.List;

public interface ResultSetHandler {
    <T> List<T> handleCursorResultSets(PreparedStatement ps);
}
