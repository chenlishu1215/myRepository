package com.itheima.utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	private final static Configuration configuration;
	private final static SessionFactory sessionFactory;
	static {
		//创建配置对象进行配置
		configuration=new Configuration();
		//加载配置文件
		configuration.configure();
		//创建session工厂
		sessionFactory = configuration.buildSessionFactory();
	}
	//把构造方法私有化防止别人new对象 破坏工具类结构
	private HibernateUtils(){
		
	}
	public static Session openSession(){
		//创建session
		return sessionFactory.openSession();
		
	}
	public static Session getCurrentSession(){
		return sessionFactory.getCurrentSession();
		
	}
}
