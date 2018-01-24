package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.bean.Comment;

public interface CommentDao {

	boolean checkRecord(int cid, String uid)throws Exception;


	void save(Comment c) throws Exception;


	void saveRecord(int cid, String uid)throws Exception;


	List<Comment> findComment(String pid)throws Exception;


	void updateComment(int cid) throws Exception;


	Comment findGood(int cid)throws Exception;

}
