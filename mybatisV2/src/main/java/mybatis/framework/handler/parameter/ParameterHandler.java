package mybatis.framework.handler.parameter;

import java.sql.PreparedStatement;

public interface ParameterHandler {
    void setParameters(PreparedStatement statement);
}
