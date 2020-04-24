package mybatis.framework.builder;

import mybatis.framework.config.Configuration;
import mybatis.framework.factory.DefaultSqlSessionFactory;
import mybatis.framework.factory.SqlSessionFactory;
import org.dom4j.Document;
import utils.DocumentUtil;

import java.io.InputStream;

public class SqlSessionFactoryBuilder {

    /**
     * 需要传入一个configuration对象，该对象由解析xml得到
     * @param inputStream io流
     * @return SqlSessionFactory
     */
    public SqlSessionFactory build(InputStream inputStream){
        Document document = DocumentUtil.createDocument(inputStream);
        if (null == document) {
            return null;
        }
        XMLConfigBuilder builder = new XMLConfigBuilder();
        Configuration configuration = builder.parseConfiguration(document.getRootElement());
        return this.build(configuration);
    }

    private SqlSessionFactory build(Configuration configuration) {
        return new DefaultSqlSessionFactory(configuration);
    }

}
