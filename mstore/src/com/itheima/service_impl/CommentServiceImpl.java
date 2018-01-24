package com.itheima.service_impl;

import java.util.List;

import org.omg.CORBA.INTERNAL;

import com.itheima.bean.Comment;
import com.itheima.dao.CommentDao;
import com.itheima.service.CommentService;
import com.itheima.utils.BeansFactory;
import com.itheima.utils.TransactionManager;

public class CommentServiceImpl implements CommentService {

	@Override
	public boolean checkRecord(int cid, String uid) throws Exception {
		CommentDao dao=(CommentDao) BeansFactory.getBean("CommentDao");
		
		return dao.checkRecord(cid,uid);
	}

	@Override
	public void save(Comment c ,String uid) throws Exception {
		CommentDao dao=(CommentDao) BeansFactory.getBean("CommentDao");
		dao.save(c);
		
	}

	@Override
	public List<Comment> findComment(String pid) throws Exception {
		CommentDao dao=(CommentDao) BeansFactory.getBean("CommentDao");
		
		return dao.findComment(pid);
	}

	@Override
	public void updateComment(int cid,String uid) throws Exception {
		try {
			CommentDao dao=(CommentDao) BeansFactory.getBean("CommentDao");
			TransactionManager.startTransaction();
			dao.updateComment(cid);
			dao.saveRecord(cid, uid);
			TransactionManager.commit();
		} catch (Exception e) {
			TransactionManager.rollback();
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public Comment findGood(int cid) throws Exception {
		CommentDao dao=(CommentDao) BeansFactory.getBean("CommentDao");
		return dao.findGood(cid);
	}

}
