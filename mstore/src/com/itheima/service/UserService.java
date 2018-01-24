package com.itheima.service;

import com.itheima.bean.User;

public interface UserService {

	void doRegister(User user) throws Exception;

	void changeState(String code)throws Exception;

	User doLogin(String username, String password)throws Exception;

	boolean checkUser(String username)throws Exception;

}
