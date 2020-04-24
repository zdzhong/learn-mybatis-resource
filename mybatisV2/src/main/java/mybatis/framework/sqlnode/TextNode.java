package mybatis.framework.sqlnode;

public class TextNode implements SqlNode {

    private String sqlText;

    public TextNode(String sqlText) {
        this.sqlText = sqlText;
    }

    @Override
    public void apply(DynamicContext context) {

    }

    public boolean isDynamic() {
        // 这里只是简单的判断是否包含 ${ 应该判断是否包含一个完整的 ${}
        if (this.sqlText.contains("${")) {
            return true;
        }
        return false;
    }
}
