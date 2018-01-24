package com.itheima.dao_impl;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import com.itheima.bean.User;
import com.itheima.dao.UserDao;
import com.itheima.utils.C3P0Util;
import com.itheima.utils.HibernateUtils;

public class UserDaoImpl implements UserDao {

	/* (non-Javadoc)
	 * 登陆业务
	 * @see com.itheima.dao.UserDao#doRegister(com.itheima.bean.User)
	 */
	@Override
	public void doRegister(User user) throws Exception {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.save(user);
		transaction.commit();
		
	}

	@Override
	public void changeState(String code,int state) throws Exception {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		User user = (User) session.createCriteria(User.class).add(Restrictions.eq("code",code)).uniqueResult();
		user.setState(state);
		
		transaction.commit();
	}

	@Override
	public void clearCode(String code) throws Exception {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		User user = (User) session.createCriteria(User.class).add(Restrictions.eq("code",code)).uniqueResult();
		user.setState(0);
		transaction.commit();
	}

	@Override
	public User findUser(String username, String password){
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		User user = (User) session.createCriteria(User.class).add(Restrictions.eq("username",username)).add(Restrictions.eq("password", password)).uniqueResult();
		transaction.commit();
		return user;
	}

	@Override
	public boolean checkUser(String username) {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<User> list =session.createCriteria(User.class).add(Restrictions.eq("username",username)).list();
		transaction.commit();
		
		
		return list.size()!=0; 
	}

}
