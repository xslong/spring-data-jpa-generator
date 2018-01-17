package cn.x.codegen.db;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.List;

/**
 * 表信息
 * @author xslong
 * @time 2017/11/6 11:53
 */
public class TableMeta {

    private String tableSchema;
    private String tableName;
    private String tableComment;

    private List<ColumnMeta> columns;
    private List<IndexMeta> indexs;

    private ColumnMeta pk;

    public String getTableSchema() {
        return tableSchema;
    }

    public void setTableSchema(String tableSchema) {
        this.tableSchema = tableSchema;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public List<ColumnMeta> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnMeta> columns) {
        this.columns = columns;
    }

    public ColumnMeta getPk() {
        return pk;
    }

    public void setPk(ColumnMeta pk) {
        this.pk = pk;
    }

    public List<IndexMeta> getIndexs() {
        return indexs;
    }

    public void setIndexs(List<IndexMeta> indexs) {
        this.indexs = indexs;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
