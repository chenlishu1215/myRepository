package com.itheima.utils;

import java.sql.Connection;
import java.sql.SQLException;


public class TransactionManager {
	//得到一个容器
	private static  ThreadLocal<Connection> local=new ThreadLocal<>();
	
	//获得连接的方法
	public static Connection getConnection() throws Exception{
		//获取容器里面的连接 
		Connection connection = local.get();
		//判断是否有获取到
		if(connection==null){
			//没有获取到 的情况 重新赋值
			connection=C3P0Util.getConnection();
			//将赋好值的connection存进容器中
			local.set(connection);
			
		}
		return connection;
	}
	
	//开启事务的方法
	public static void startTransaction() throws SQLException, Exception{
		getConnection().setAutoCommit(false);
	}
	
	//提交事务的方法
	public static void commit() throws SQLException, Exception{
		getConnection().commit();
	}
	
	//事务回滚的方法
	public static void rollback() throws SQLException, Exception{
		getConnection().rollback();
	}
}
