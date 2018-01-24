package com.itheima.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 配置文件的方式使用C3P0连接好吃
 *
 */
public class C3P0Util {
	private static ComboPooledDataSource cpds;
	static{
		cpds = new ComboPooledDataSource();
		//将参数从xml文件中读取出来，然后设置到cpds中去
	}
	/**
	 * 获取连接
	 * @return
	 * @throws SQLException 
	 */
	public static Connection getConnection() throws Exception{
		//从C3P0连接池中获取
		//3.从连接池中获取连接
		Connection connection = cpds.getConnection();
		return connection;
	}
	public static DataSource getDataSource(){
		return cpds;
	}
	public static void release(ResultSet resultSet, Statement statement, Connection connection) throws SQLException{
		if (resultSet != null) {
			resultSet.close();
		}
		if (statement != null) {
			statement.close();
		}
		if (connection != null) {
			connection.close();
		}
	}
}
