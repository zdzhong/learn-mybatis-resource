package mybatis.framework.builder;

import mybatis.framework.config.Configuration;
import mybatis.framework.handler.DataSourceHandler;
import mybatis.framework.io.Resources;
import org.apache.commons.dbcp.BasicDataSource;
import org.dom4j.Document;
import org.dom4j.Element;
import utils.DocumentUtil;

import javax.sql.DataSource;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

public class XMLConfigBuilder {

    private Configuration configuration;

    public XMLConfigBuilder() {
        this.configuration = new Configuration();
    }

    public Configuration parseConfiguration(Element rootElement) {
        // 解析environments节点
        parseEnvironments(rootElement.element("environments"));
        // 解析mappers节点
        parseMappers(rootElement.element("mappers"));
        return configuration;
    }

    @SuppressWarnings("unchecked")
    private void parseEnvironments(Element environments) {
        String defaultId = environments.attributeValue("default");
        List<Element> elements = environments.elements();
        for (Element element : elements) {
            String id = element.attributeValue("id");
            if (defaultId.equals(id)) {
                parseDataSource(element);
            }
        }
    }

    private void parseDataSource(Element element) {
        Element dataSourceElement = element.element("dataSource");
        String type = dataSourceElement.attributeValue("type");
        DataSourceHandler handler = new DataSourceHandler();
        BasicDataSource dataSource = (BasicDataSource) handler.getDataSource(type.toLowerCase());
        Properties properties = parseProperty(dataSourceElement);
        dataSource.setDriverClassName(properties.getProperty("driver"));
        dataSource.setUrl(properties.getProperty("url"));
        dataSource.setUsername(properties.getProperty("username"));
        dataSource.setPassword(properties.getProperty("password"));
        configuration.setDataSource(dataSource);
    }

    @SuppressWarnings("unchecked")
    private Properties parseProperty(Element dataSourceElement) {
        Properties properties = new Properties();
        List<Element> propertyList = dataSourceElement.elements("property");
        for (Element element : propertyList) {
            String name = element.attributeValue("name");
            String value = element.attributeValue("value");
            properties.put(name, value);
        }
        return properties;
    }

    @SuppressWarnings("unchecked")
    private void parseMappers(Element mappers) {
        List<Element> mapperList = mappers.elements("mapper");
        for (Element element : mapperList) {
            String resource = element.attributeValue("resource");
            InputStream inputStream = Resources.getResourceAsStream(resource);
            Document document = DocumentUtil.createDocument(inputStream);
            if (null == document) {
                continue;
            }
            XMLMapperBuilder mapperBuilder = new XMLMapperBuilder(configuration);
            mapperBuilder.parse(document.getRootElement());
        }
    }
}
