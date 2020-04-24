package mybatis.framework.sqlnode;

import utils.OgnlUtils;

public class IfSqlNode implements SqlNode {
    private String test;
    private MixedSqlNode mixedSqlNode;

    public IfSqlNode(String test, MixedSqlNode mixedSqlNode) {
        this.test = test;
        this.mixedSqlNode = mixedSqlNode;
    }

    @Override
    public void apply(DynamicContext context) {
        // 需要使用到Ognl工具类
        boolean evaluateBoolean = OgnlUtils.evaluateBoolean(test, context.getBindings().get("_parameter"));
        if (evaluateBoolean) {
            mixedSqlNode.apply(context);
        }
    }
}
