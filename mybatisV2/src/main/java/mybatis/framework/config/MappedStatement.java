package mybatis.framework.config;

import mybatis.framework.sqlsource.SqlSource;

public class MappedStatement {
    private String statementId;
    private SqlSource sqlSource;
    private String statementType;
    private Class<?> parameterTypeClass;
    private Class<?> resultTypeClass;
    private String resultSetType;

    public String getStatementId() {
        return statementId;
    }

    public String getResultSetType() {
        return resultSetType;
    }

    public void setStatementId(String statementId) {
        this.statementId = statementId;
    }

    public SqlSource getSqlSource() {
        return sqlSource;
    }

    public void setSqlSource(SqlSource sqlSource) {
        this.sqlSource = sqlSource;
    }

    public String getStatementType() {
        return statementType;
    }

    public void setStatementType(String statementType) {
        this.statementType = statementType;
    }

    public Class<?> getParameterTypeClass() {
        return parameterTypeClass;
    }

    public void setParameterTypeClass(Class<?> parameterTypeClass) {
        this.parameterTypeClass = parameterTypeClass;
    }

    public Class<?> getResultTypeClass() {
        return resultTypeClass;
    }

    public void setResultTypeClass(Class<?> resultTypeClass) {
        this.resultTypeClass = resultTypeClass;
    }

    public static class Builder{
        private MappedStatement mappedStatement = new MappedStatement();

        public Builder(String statementId, SqlSource sqlSource){
            mappedStatement.statementId = statementId;
            mappedStatement.sqlSource = sqlSource;
            mappedStatement.resultSetType = "default";
        }

        public Builder statementType(String statementType) {
            mappedStatement.statementType = statementType;
            return this;
        }

        public Builder parameterTypeClass(Class<?> parameterTypeClass) {
            mappedStatement.parameterTypeClass = parameterTypeClass;
            return this;
        }

        public Builder resultTypeClass(Class<?> resultTypeClass) {
            mappedStatement.resultTypeClass = resultTypeClass;
            return this;
        }

        public MappedStatement build(){
            return mappedStatement;
        }
    }
}
