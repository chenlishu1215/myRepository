package com.itheima.utils;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.DbUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class JDBCUtils {
    private static DataSource ds = new ComboPooledDataSource();
    //返回获取连接池的方法
    public static DataSource getDataSource(){
        return ds;
    }

    private static ThreadLocal<Connection> tl = new ThreadLocal<Connection>();

    public static Connection getConnection() throws SQLException {
        Connection conn = tl.get();
        if(conn == null){
            conn = ds.getConnection();
            tl.set(conn);
        }
        return conn;
    }
    //开始事务
    public static void beginTransaction() throws SQLException {
        Connection conn = getConnection();
        conn.setAutoCommit(false);
    }

    //结束事务
    public static void commitAndClose(){
        Connection conn = null;
        try {
            conn = getConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        DbUtils.commitAndCloseQuietly(conn);
        tl.remove();
    }

    //回滚事务
    public static void rollbackAndClose(){
        try {
            Connection conn = getConnection();
            DbUtils.rollbackAndCloseQuietly(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
