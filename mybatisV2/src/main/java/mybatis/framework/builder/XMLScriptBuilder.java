package mybatis.framework.builder;

import mybatis.framework.sqlnode.*;
import mybatis.framework.sqlsource.DynamicSqlSource;
import mybatis.framework.sqlsource.RawSqlSource;
import mybatis.framework.sqlsource.SqlSource;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class XMLScriptBuilder {
    private boolean isDynamic;

    private final Map<String, ElementHandler> nodeHandlerMap = new HashMap<>();

    public XMLScriptBuilder(){
        initElementHandlerMap();
    }

    private void initElementHandlerMap() {
        nodeHandlerMap.put("if", new IfHandler());
    }

    public SqlSource parseScriptNode(Element element) {
        MixedSqlNode rootSqlNode = parseDynamicTags(element);
        SqlSource sqlSource = null;
        if (isDynamic) {
            sqlSource = new DynamicSqlSource(rootSqlNode);
        } else {
            sqlSource = new RawSqlSource(rootSqlNode);
        }
        return sqlSource;
    }

    private MixedSqlNode parseDynamicTags(Element element) {
        List<SqlNode> sqlNodes = new ArrayList<>();
        int nodeCount = element.nodeCount();
        for (int i = 0; i < nodeCount; i++) {
            Node node = element.node(i);
            if (node instanceof Text) {
                String sqlText = node.getText().trim();
                if ("".equals(sqlText)) {
                    continue;
                }
                TextNode textNode = new TextNode(sqlText);
                if (textNode.isDynamic()) {
                    sqlNodes.add(textNode);
                    this.isDynamic = true;
                } else {
                    sqlNodes.add(new StaticTextSqlNode(sqlText));
                }
            } else if (node instanceof Element) {
                Element dynamicElement = (Element) node;
                String name = dynamicElement.getName();
                ElementHandler handler = nodeHandlerMap.get(name);
                if (handler == null) {
                    throw new RuntimeException("Unknown element <" + name + "> in SQL statement.");
                }
                handler.handleElement(dynamicElement, sqlNodes);
                isDynamic = true;
            }
        }
        return new MixedSqlNode(sqlNodes);
    }

    private interface ElementHandler {
        void handleElement(Element element, List<SqlNode> targetContents);
    }

    private class IfHandler implements ElementHandler {
        @Override
        public void handleElement(Element element, List<SqlNode> targetContents) {
            String test = element.attributeValue("test");
            MixedSqlNode mixedSqlNode = parseDynamicTags(element);
            IfSqlNode ifSqlNode = new IfSqlNode(test, mixedSqlNode);
            targetContents.add(ifSqlNode);
        }
    }
}
