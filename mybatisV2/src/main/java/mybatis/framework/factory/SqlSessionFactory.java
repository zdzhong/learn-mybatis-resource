package mybatis.framework.factory;

import mybatis.framework.sqlsession.SqlSession;

public interface SqlSessionFactory {
    SqlSession openSession();
}
