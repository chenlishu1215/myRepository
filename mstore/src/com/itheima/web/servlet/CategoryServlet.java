package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.itheima.bean.Category;
import com.itheima.service.ProductService;
import com.itheima.service_impl.ProductServiceImpl;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Servlet implementation class CategoryServlet
 */
public class CategoryServlet extends ActionSupport {
	
	
	public String findAll() throws IOException{
		
		ProductService service=new ProductServiceImpl();
		String json= service.findAll();
		HttpServletResponse response = ServletActionContext.getResponse();
		response.getWriter().write(json);
		
		return NONE;
		
		
	}

	

}
