package com.itheima.service_impl;

import com.itheima.bean.User;
import com.itheima.constant.Constant;
import com.itheima.dao.UserDao;
import com.itheima.dao_impl.UserDaoImpl;
import com.itheima.service.UserService;
import com.itheima.utils.BeansFactory;
import com.itheima.utils.MD5Utils;
import com.itheima.utils.MailUtils;

public class UserServiceImpl  implements UserService{

	@Override
	public void doRegister(User user) throws Exception {
		UserDao dao=(UserDao) BeansFactory.getBean("UserDao");
		//获取出密码进行加密
		String password = user.getPassword();
		//md5加盐 加密操作 
		String psd = MD5Utils.encode(password, Constant.SALT);
		user.setPassword(psd);
		
		dao.doRegister(user);
		//发送邮件
		MailUtils.sendMail(user.getEmail(), "激活邮件", "欢迎使用本商城的服务 激活帐号请点击 <a href='http://localhost:8080/supermarket/user?method=active&code="+user.getCode()+"'>激活帐号</a> &nbsp;如果点击无效请复制此链接到地址栏打开 <a href='http://localhost:8080/supermarket/user?method=active&code="+user.getCode()+"'>http://localhost:8080/supermarket/user?method=active&code="+user.getCode()+"</a>");
		
	}

	@Override
	public void changeState(String code) throws Exception {
		UserDao dao=(UserDao) BeansFactory.getBean("UserDao");
		dao.changeState(code,Constant.ACTIVE);
		dao.clearCode(code);
	}

	@Override
	public User doLogin(String username, String password) throws Exception {
		UserDao dao=(UserDao) BeansFactory.getBean("UserDao");
		
		String psd = MD5Utils.encode(password, Constant.SALT);
		
		return dao.findUser(username,psd);
	}

	@Override
	public boolean checkUser(String username) throws Exception {
		UserDao dao=(UserDao) BeansFactory.getBean("UserDao");
		return dao.checkUser(username);
	}

}
