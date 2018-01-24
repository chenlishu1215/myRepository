package com.itheima.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;

import cn.dsna.util.images.ValidateCode;

/**
 * Servlet implementation class ValidateServlet
 */
public class ValidateServlet extends ActionSupport {
	private static final long serialVersionUID = 1L;

	/**
	 * 生成验证码
	 */
	public void getValidate() throws ServletException, IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		ValidateCode validate=new ValidateCode(90, 36, 4, 8);
		
		String code = validate.getCode();
		request.getSession().setAttribute("code", code);
		
		ServletOutputStream outputStream = response.getOutputStream();
		validate.write(outputStream);
	}

	

}
