package com.itheima.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.itheima.bean.Cart;
import com.itheima.bean.CartItems;
import com.itheima.bean.User;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Servlet implementation class CartServlet
 */
public class CartServlet extends ActionSupport {
	private static final long serialVersionUID = 1L;

	/**添加商品到购物车
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public  String addProduct() throws ServletException, IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String pid = request.getParameter("pid");
		int count=Integer.parseInt(request.getParameter("count"));
		Cart cart=(Cart) request.getSession().getAttribute("cart");
		if(cart==null){
		 cart=new Cart();
		}
		
		cart.addProduct(pid, count);
		request.getSession().setAttribute("cart", cart);
	
		
		return "success";
		
	}
	//删除商品
	public  String deleteProduct() throws ServletException, IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		 
		
		String pid = request.getParameter("pid");
		
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if(cart!=null){
			cart.deleteProduct(pid);
		}
		
		
		return "success";
	
	
	}
	public  String clearCart() throws ServletException, IOException {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if(cart!=null){
			cart.clearCart();
		}
		
		
		return "success";
		
		
	}
	public  String saveCount() throws ServletException, IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		String pid = request.getParameter("pid");
		Cart cart=(Cart) request.getSession().getAttribute("cart");
		try {
			int count= Integer.parseInt(request.getParameter("count"));
			CartItems cartItems = cart.getMaps().get(pid);
			cartItems.setCount(count);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return NONE;
	
	}
	
}
