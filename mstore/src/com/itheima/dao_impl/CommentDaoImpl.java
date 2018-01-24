package com.itheima.dao_impl;



import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import com.itheima.bean.Comment;
import com.itheima.bean.Record;
import com.itheima.dao.CommentDao;
import com.itheima.utils.C3P0Util;
import com.itheima.utils.HibernateUtils;
import com.itheima.utils.TransactionManager;

public class CommentDaoImpl implements CommentDao{

	@Override
	public boolean checkRecord(int cid, String uid) throws Exception {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Criteria criteria = session.createCriteria(Record.class);
		criteria.add(Restrictions.eq("cid", cid)).add(Restrictions.eq("uid", uid));
		Long rows = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();
		transaction.commit();
		
		return rows.intValue()>0;
	}

	@Override
	public void save(Comment comment) throws Exception {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		session.save(comment);
		transaction.commit();
		
	}

	@Override
	public void saveRecord(int cid, String uid) throws Exception {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Record record=new Record();
		record.setCid(cid);
		record.setUid(uid);
		session.save(record);
		transaction.commit();
			
		
		
	}

	@Override
	public List<Comment> findComment(String pid) throws Exception {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Criteria criteria = session.createCriteria(Comment.class);
		List<Comment> list = criteria.add(Restrictions.eq("pid", pid)).list();
		
		return list;
	}

	@Override
	public void updateComment(int cid) throws Exception {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Comment comment = session.get(Comment.class, cid);
		comment.setGood(comment.getGood()+1);
		transaction.commit();
	}

	@Override
	public Comment findGood(int cid) {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Comment comment = session.get(Comment.class,cid);
		transaction.commit();
		return comment;
	}

}
