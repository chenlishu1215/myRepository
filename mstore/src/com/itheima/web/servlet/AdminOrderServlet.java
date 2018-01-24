package com.itheima.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.itheima.bean.Order;
import com.itheima.bean.PageBean;
import com.itheima.constant.Constant;
import com.itheima.service.OrderService;
import com.itheima.service_impl.OrderServiceImpl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * 
 */
public class AdminOrderServlet extends ActionSupport {
	private Order order;

	
	public Order getOrder() {
		return order;
	}
	public  String findOrderByPage() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		int curPage = Integer.parseInt(request.getParameter("curPage"));
		String state = request.getParameter("state");
		int pageSize=Constant.ADMIN_PAGE_SIZE;
		OrderService service=new OrderServiceImpl();
		PageBean<Order> pageBean = service.findOrderByPage(state, curPage, pageSize);
		request.setAttribute("pageBean", pageBean);
		
		return "findOrderByPageSuccess";
		
		
	}
	public  void updateState() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		int state = Integer.parseInt(request.getParameter("state"));
		String oid = request.getParameter("oid");
		if(state<=3 && state>=0){
			OrderService service=new OrderServiceImpl();
			service.updateOrderState(state, oid);
			
		}
		
		response.sendRedirect(request.getContextPath()+"/adminOrderServlet_findOrderByPage?curPage=1&state="+(state-1));
		
		
	}
	public  String showDetail() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String oid=request.getParameter("oid");
		OrderService service=new OrderServiceImpl();
		order = service.findOrder(oid);
		
		//String json = new Gson().toJson(order);
		//response.getWriter().write(json);
		
		return "showDetailSuccess";
		
	}

}
