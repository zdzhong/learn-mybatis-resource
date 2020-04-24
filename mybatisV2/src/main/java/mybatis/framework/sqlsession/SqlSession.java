package mybatis.framework.sqlsession;

public interface SqlSession {
    <T> T selectOne(String statementId, Object paramObject);
}
