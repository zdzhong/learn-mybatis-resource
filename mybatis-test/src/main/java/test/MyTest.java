package test;

import entry.Blog;
import mybatis.framework.builder.SqlSessionFactoryBuilder;
import mybatis.framework.factory.SqlSessionFactory;
import mybatis.framework.io.Resources;
import mybatis.framework.sqlsession.SqlSession;

import java.io.InputStream;

public class MyTest {
    public static void main(String[] args) {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        SqlSession sqlSession = sqlSessionFactory.openSession();
//        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
//        Blog blog = mapper.selectBlog(101);
        Blog blog1 = new Blog();
        blog1.setId(101);
        Blog blog = sqlSession.selectOne("mapper.selectBlog", blog1);
        System.out.println(blog);
    }
}
