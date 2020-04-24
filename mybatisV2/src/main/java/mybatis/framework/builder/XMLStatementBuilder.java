package mybatis.framework.builder;

import mybatis.framework.config.Configuration;
import mybatis.framework.config.MappedStatement;
import mybatis.framework.sqlsource.SqlSource;
import org.dom4j.Element;
import utils.ReflectUtils;

public class XMLStatementBuilder {
    private Configuration configuration;

    private String namespace;

    public XMLStatementBuilder(Configuration configuration, String namespace) {
        this.configuration = configuration;
        this.namespace = namespace;
    }

    public void parseStatement(Element element) {
        String id = element.attributeValue("id");
        String statementId = namespace + "." + id;
        // resultType
        String resultType = element.attributeValue("resultType");
        Class<?> resultClass = ReflectUtils.resolveType(resultType);
        // parameterType
        String parameterType = element.attributeValue("parameterType");
        Class<?> parameterClass = ReflectUtils.resolveType(parameterType);
        // statementType
        String statementType = element.attributeValue("statementType");
        statementType = null == statementType|| "".equals(statementType) ? "prepared" : statementType;
        SqlSource sqlSource = createSqlSource(element);
        MappedStatement.Builder builder = new MappedStatement.Builder(statementId, sqlSource)
                .resultTypeClass(resultClass)
                .statementType(statementType)
                .parameterTypeClass(parameterClass);
        configuration.addMappedStatement(builder.build());
    }

    private SqlSource createSqlSource(Element element) {
        XMLScriptBuilder scriptBuilder = new XMLScriptBuilder();
        return scriptBuilder.parseScriptNode(element);
    }
}
