package com.itheima.dao;

import com.itheima.bean.User;

public interface UserDao {

	void doRegister(User user) throws Exception;

	void changeState(String code,int state)throws Exception;

	void clearCode(String code)throws Exception;

	User findUser(String username, String password);

	boolean checkUser(String username);

	

}
