package com.itheima.dao_impl;

import java.sql.SQLException;

import javax.management.Query;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.itheima.bean.Category;
import com.itheima.dao.CategoryDao;
import com.itheima.utils.C3P0Util;
import com.itheima.utils.HibernateUtils;

public class CategoryDaoImpl implements CategoryDao {

	@Override
	public void delCategory(String cid) {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Category category = session.get(Category.class, cid);
		session.delete(category);
		transaction.commit();
		
	}

	@Override
	public Category categoryBackShow(String cid) {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Category category = session.get(Category.class, cid);
		transaction.commit();
		return category;
	}

	@Override
	public void updateCategory(Category c) {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Category category = session.get(Category.class, c.getCid());
		category.setCname(c.getCname());
		transaction.commit();
		
				
		
	}

	@Override
	public void addCategory(String cid, String cname) {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Category category=new Category();
		category.setCid(cid);
		category.setCname(cname);
		session.save(category);
		transaction.commit();
		
	}

}
