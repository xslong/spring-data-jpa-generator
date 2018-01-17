package cn.x.codegen.db;

import cn.x.codegen.utils.SQLUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 分析数据表结构
 * @author xslong
 * @time 2017/11/6 11:53
 */
public class AnalysisDB {

    private Connection connection;

    public AnalysisDB(Connection connection) {
        this.connection = connection;
    }

    public List<TableMeta> allTable(String tableSchema) {
        return allTable(tableSchema, null);
    }

    /**
     * 读取表列表
     * @return
     */
    public List<TableMeta> allTable(String tableSchema, String prefix) {
        List<TableMeta> list = new ArrayList<TableMeta>();
        String sql = "SELECT TABLE_SCHEMA,TABLE_NAME ,TABLE_COMMENT FROM information_schema.tables "
                + " WHERE table_schema ='" + tableSchema + "'";
        if (prefix != null) {
            sql += " and table_name like '" + prefix + "%'";
        }
        SQLUtils.execute(connection, sql, new SQLUtils.ResultSetLoop() {
            @Override
            public void each(int count, ResultSet rs) throws SQLException {
                TableMeta tm = new TableMeta();
                tm.setTableSchema(rs.getString("TABLE_SCHEMA"));
                tm.setTableName(rs.getString("TABLE_NAME"));
                tm.setTableComment(rs.getString("TABLE_COMMENT"));
                list.add(tm);
            }
        });
        return list;
    }

    public List<ColumnMeta> allColumn(String tableSchema, String tableName) {
        String sql = "SELECT TABLE_NAME,COLUMN_NAME,ORDINAL_POSITION,COLUMN_DEFAULT,IS_NULLABLE,COLUMN_KEY,DATA_TYPE,CHARACTER_MAXIMUM_LENGTH," +
                "NUMERIC_PRECISION,NUMERIC_SCALE,COLUMN_TYPE,EXTRA,COLUMN_COMMENT " +
                "from  information_schema.columns"
                + " WHERE table_schema ='" + tableSchema + "' and table_name = '" + tableName + "'";
        List<ColumnMeta> list = new ArrayList<>();
        SQLUtils.execute(connection, sql, new SQLUtils.ResultSetLoop() {
            @Override
            public void each(int count, ResultSet rs) throws SQLException {
                ColumnMeta cm = new ColumnMeta();
                cm.setTableName(rs.getString("TABLE_NAME"));
                cm.setColumnName(rs.getString("COLUMN_NAME"));
                if (rs.getObject("ORDINAL_POSITION") != null) {
                    cm.setOrdinalPosition(rs.getInt("ORDINAL_POSITION"));
                }
                cm.setColumnDefault(rs.getString("COLUMN_DEFAULT"));
                cm.setIsNullable(rs.getString("IS_NULLABLE"));
                cm.setDataType(rs.getString("DATA_TYPE"));

                if (rs.getObject("CHARACTER_MAXIMUM_LENGTH") != null) {
                    cm.setCharacterMaximumLength(rs.getInt("CHARACTER_MAXIMUM_LENGTH"));
                }
                if (rs.getObject("NUMERIC_PRECISION") != null) {
                    cm.setNumericPrecision(rs.getInt("NUMERIC_PRECISION"));
                }
                if (rs.getObject("NUMERIC_SCALE") != null) {
                    cm.setNumericScale(rs.getInt("NUMERIC_SCALE"));
                }
                cm.setColumnKey(rs.getString("COLUMN_KEY"));
                cm.setColumnType(rs.getString("COLUMN_TYPE"));
                cm.setExtra(rs.getString("EXTRA"));
                cm.setColumnComment(rs.getString("COLUMN_COMMENT"));
                list.add(cm);
            }
        });
        return list;
    }

    public List<IndexMeta> allIndex(String tableSchema, String tableName) {
        String sql = "select INDEX_NAME,COLUMN_NAME,NON_UNIQUE,TABLE_NAME,INDEX_TYPE\n" +
                "from information_schema.STATISTICS\n" +
                "where TABLE_SCHEMA = '" + tableSchema + "'\n" +
                "and INDEX_NAME <> 'PRIMARY'\n" +
                "and TABLE_NAME = '" + tableName + "'\n" +
                "order by SEQ_IN_INDEX";
        List<IndexMeta> list = new ArrayList<>();
        SQLUtils.execute(connection, sql, new SQLUtils.ResultSetLoop() {
            @Override
            public void each(int count, ResultSet rs) throws SQLException {
                IndexMeta im = new IndexMeta();
                im.setIndexName(rs.getString("INDEX_NAME"));
                im.setColumnNames(rs.getString("COLUMN_NAME"));
                im.setNonUnique(rs.getInt("NON_UNIQUE"));
                im.setTableName(rs.getString("TABLE_NAME"));
                im.setIndexType(rs.getString("INDEX_TYPE"));
                list.add(im);
            }
        });
        Map<String, List<IndexMeta>> newMap = list.stream().collect(Collectors.groupingBy(IndexMeta::getIndexName, Collectors.toList()));
        List<IndexMeta> result = new ArrayList<>();
        newMap.forEach((key, metas) -> {
            if (metas.size() != 1) {
                String columnNames = StringUtils.join(metas.stream().map(IndexMeta::getColumnNames).collect(Collectors.toList()), ",");
                metas.get(0).setColumnNames(columnNames);
            }
            result.add(metas.get(0));
        });
        return result;
    }


    public ColumnMeta pk(List<ColumnMeta> columnMetas) {
        for (ColumnMeta col : columnMetas) {
            if ("PRI".equals(col.getColumnKey())) {
                return col;
            }
        }
        return null;
    }

}
