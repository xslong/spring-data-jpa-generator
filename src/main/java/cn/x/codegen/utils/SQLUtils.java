package cn.x.codegen.utils;

import java.sql.*;

/**
 * @author xslong
 * @time 2017/11/6 12:01
 */
public class SQLUtils {

    public static void close(Statement ps) {
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public static void close(ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    public static void execute(Connection connection, String sql, ResultSetLoop rsl) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
                rsl.each(count, rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLUtils.close(rs);
            SQLUtils.close(ps);
        }
    }

   public interface ResultSetLoop {
        void each(int count, ResultSet rs) throws SQLException;

    }


}




