package com.itheima.service;

import java.util.List;

import com.itheima.bean.Comment;

public interface CommentService {

	boolean checkRecord(int cid, String uid) throws Exception;

	void save(Comment c, String pid)throws Exception;

	List<Comment> findComment(String pid)throws Exception;

	void updateComment(int cid, String uid)throws Exception;

	Comment findGood(int cid) throws Exception;

}
