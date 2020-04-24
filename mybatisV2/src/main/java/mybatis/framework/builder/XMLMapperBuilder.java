package mybatis.framework.builder;

import mybatis.framework.config.Configuration;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;

public class XMLMapperBuilder {
    private Configuration configuration;

    public XMLMapperBuilder(Configuration configuration) {
        this.configuration = configuration;
    }

    @SuppressWarnings("unchecked")
    public void parse(Element rootElement) {
        String namespace = rootElement.attributeValue("namespace");
        List<Element> crudElements = new ArrayList<>();
        crudElements.addAll(rootElement.elements("select"));
        crudElements.addAll(rootElement.elements("update"));
        crudElements.addAll(rootElement.elements("insert"));
        crudElements.addAll(rootElement.elements("delete"));
        for (Element element : crudElements) {
            XMLStatementBuilder statementBuilder = new XMLStatementBuilder(configuration, namespace);
            statementBuilder.parseStatement(element);
        }
    }
}
