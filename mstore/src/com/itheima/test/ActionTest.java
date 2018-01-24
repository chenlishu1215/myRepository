package com.itheima.test;

import org.hibernate.Hibernate;
import org.hibernate.Session;

import com.itheima.utils.HibernateUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class ActionTest extends ActionSupport {
	public void test01(){
		System.out.println("test01执行了！");
	}

}
