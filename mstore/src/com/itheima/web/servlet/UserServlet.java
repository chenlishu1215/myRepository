package com.itheima.web.servlet;


import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;

import com.itheima.bean.User;
import com.itheima.constant.Constant;
import com.itheima.service.UserService;
import com.itheima.service_impl.UserServiceImpl;
import com.itheima.utils.BeansFactory;
import com.itheima.utils.CookieUtils;
import com.itheima.utils.UUIDUtils;
import com.opensymphony.xwork2.ActionSupport;

/**
 * 
 */
public class UserServlet extends ActionSupport {
	
	public String register(){
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, String[]> map = request.getParameterMap();
		String vcode = request.getParameter("vcode");
		
		String  code = (String) request.getSession().getAttribute("code");
		User user=new User();
		try {
			BeanUtils.populate(user, map);
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//验证码正确
		if(code.equalsIgnoreCase(vcode)){
			
		
			try {
				
				user.setUid(UUIDUtils.getId());
				user.setState(Constant.UNACTIVE);
				user.setCode(UUIDUtils.getId());
				
				UserService service=(UserService) BeansFactory.getBean("UserService");
				
				service.doRegister(user);
				//response.sendRedirect(request.getContextPath()+"/jsp/success.jsp");
				return "registerSuccess";
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("msg", "注册失败，请仔细检查输入的内容");
				return "registerFailed";
				
			}
			//验证码出错
		}else{
			request.setAttribute("user", user);
			request.setAttribute("msg", "验证码错误，请重新输入");
			return "registerFailed";
		}
		
	}
	
	public String login() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String vcode = request.getParameter("vcode");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String rem = request.getParameter("rem");
		String auto = request.getParameter("auto");
		
		
		HttpSession session = request.getSession();
		String code = (String)session.getAttribute("code");
		request.setAttribute("username", username);
		if(code.equalsIgnoreCase(vcode)){
			UserService service=(UserService) BeansFactory.getBean("UserService");
			User user= service.doLogin(username,password);
			if(user!=null){
				//账户未激活 提示用户激活账户
				if(user.getState()==0){
					request.setAttribute("msg", "您的账户未激活，请激活后使用");
					return "loginFailed";
				}else{
					session.setAttribute("user", user);
					
					int second=0;
					
					String value="";
					if("1".equals(rem) && "1".equals(auto)){
						//有效期设为7天
						second=Constant.second;
						value=username+"#"+password;
						
					}else if("1".equals(rem)){
						second=Constant.second;
					}
					CookieUtils.createCookie(response, "user", value, second, request.getContextPath());
					CookieUtils.createCookie(response, "username", username, second, request.getContextPath());
					//response.sendRedirect(request.getContextPath()+"/jsp/index.jsp");
					return "loginSuccess";
				}
			}else{
				request.setAttribute("msg","用户名或密码错误，请重新输入");
				return "loginFailed";
			}
			
		}else{
			request.setAttribute("msg", "验证码错误，请重新输入");
			
			request.setAttribute("password", password);
			return "loginFailed";
		}
		
		
		
	}
	public String active(){
		HttpServletRequest request = ServletActionContext.getRequest();
		
		String code = request.getParameter("code");
		try {
			UserService service=(UserService) BeansFactory.getBean("UserService");
			service.changeState(code);
			//response.sendRedirect(request.getContextPath()+"/jsp/login.jsp");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "loginFailed";
		
		
	}
	public String exit() throws IOException{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		//request.getSession().removeAttribute("user");
		//是session失效
		request.getSession().invalidate();
		Cookie cookie = new Cookie("user", "");
		cookie.setMaxAge(0);
		cookie.setPath(request.getContextPath());
		response.addCookie(cookie);
		//response.sendRedirect(request.getContextPath()+"/index.jsp");
		
		
		return "loginSuccess";
		
	}
	public String unameAjax() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String username = request.getParameter("username");
		
		UserService service=(UserService) BeansFactory.getBean("UserService");
		
		boolean flag=service.checkUser(username);
		if(flag){
			response.getWriter().write("true");
		}
		return NONE;
		
		
	}

}
