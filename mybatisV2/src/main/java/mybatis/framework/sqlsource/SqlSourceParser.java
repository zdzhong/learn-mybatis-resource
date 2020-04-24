package mybatis.framework.sqlsource;

import utils.GenericTokenParser;
import utils.ParameterMappingTokenHandler;

public class SqlSourceParser {

    public SqlSource parse(String sqlText) {
        ParameterMappingTokenHandler tokenHandler = new ParameterMappingTokenHandler();
        GenericTokenParser tokenParser = new GenericTokenParser("#{", "}", tokenHandler);
        String sql = tokenParser.parse(sqlText);
        return new StaticSqlSource(sql, tokenHandler.getParameterMappings());
    }
}
