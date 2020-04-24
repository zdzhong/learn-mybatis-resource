package framework;

import entry.Blog;
import utils.SimpleClassUtil;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.Properties;

public class MybatisV1 {

    public Blog selectOne(String statementId, Object objectParam) throws Exception{
        // 加载配置文件
        Properties properties = loadProperties();
        // 获取数据库连接
        Connection connection = getConnection(properties);
        // 获取sql
        String sqlStr = properties.getProperty("jdbc.sql." + statementId);
        // 获取预处理statement
        PreparedStatement statement =  getStatement(connection, sqlStr);
        // 处理入参
        parseStatement(statement, objectParam, properties);
        // 执行sql语句
        ResultSet resultSet = statement.executeQuery();
        // 处理返回结果
        return warpResult(resultSet, Blog.class);
    }

    private <T> T warpResult(ResultSet resultSet, Class<T> clazz) throws Exception {
        // 通过反射获取对象实例
        T t = clazz.getConstructor().newInstance();
        while (resultSet.next()) {
            // 获取每一行中所有列的信息
            ResultSetMetaData metaData = resultSet.getMetaData();
            // 遍历mataData获取每个列中的信息
            for (int i = 0; i < metaData.getColumnCount(); i++) {
                // 通过索引获取某个列的信息，从1开始
                String column1 = metaData.getColumnName(i + 1);
                // 通过反射将结果集中的字段名与实体对象中的属性名相对应
                // 需要使用getDeclaredField() 方法获取私有属性
                Field f = clazz.getDeclaredField(column1);
                // 当isAccessible()的结果是false时不允许通过反射访问该字段
                // 当该字段时private修饰时isAccessible()得到的值是false，必须要改成true才可以访问
                f.setAccessible(true);
                // 将结果集中的值赋给相应的对象实体的属性
                f.set(t, resultSet.getObject(column1));
            }
        }
        return t;
    }

    private void parseStatement(PreparedStatement statement, Object objectParam, Properties properties) throws Exception {
        Class<?> clazz = objectParam.getClass();
        if(SimpleClassUtil.isSimpleClass(clazz)){
            statement.setObject(1, objectParam);
        }else {
            String params = properties.getProperty("jdbc.sql.params");
            String[] paramArr = params.split(",");
            for (int i = 0; i < paramArr.length; i++) {
                String param = paramArr[i];
                Field field = clazz.getDeclaredField(param);
                field.setAccessible(true);
                statement.setObject(i + 1, field.get(objectParam));
            }
        }
    }

    private PreparedStatement getStatement(Connection connection, String sql) throws Exception {
        return connection.prepareStatement(sql);
    }

    private Properties loadProperties() throws IOException {
        InputStream inputStream = this.getClass().getResourceAsStream("/JDBC.properties");
        Properties properties = new Properties();
        properties.load(inputStream);
        return properties;
    }

    private Connection getConnection(Properties properties) throws Exception {
        Class.forName(properties.getProperty("jdbc.driver"));
        return DriverManager.getConnection(properties.getProperty("jdbc.url"), properties.getProperty("jdbc.username"), properties.getProperty("jdbc.password"));
    }

}
