package com.itheima.web.servlet;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;
import org.apache.struts2.ServletActionContext;

import com.itheima.bean.Category;
import com.itheima.bean.Comment;
import com.itheima.bean.PageBean;
import com.itheima.bean.Product;
import com.itheima.bean.User;
import com.itheima.constant.Constant;
import com.itheima.service.CommentService;
import com.itheima.service.ProductService;

import com.itheima.service_impl.ProductServiceImpl;
import com.itheima.utils.BeansFactory;
import com.itheima.utils.DateUtils;
import com.itheima.utils.UUIDUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


/**
 * Servlet implementation class ProductServlet
 */@SuppressWarnings("unused")
public class ProductServlet extends ActionSupport {
	
	
	/**查找 最新最热商品
	 * @param request
	 * @param response
	 * @return
	 */
	public String indexProduct(){
		ProductService service=new ProductServiceImpl();
		
		List<Product> hotProduct=service.findHotProduct(Constant.PRODUCT_COUNT);
		List<Product> newProduct =service.findNewProduct(Constant.PRODUCT_COUNT);
		HttpServletRequest request = ServletActionContext.getRequest();
		request.getSession().setAttribute("hotProduct", hotProduct);
		request.getSession().setAttribute("newProduct", newProduct);
		
		return "indexProductSuccess";
	}
	
	/**根据分类cid查询商品
	 * @param request
	 * @param response
	 * @return
	 */
	public String findByPage(){
		HttpServletRequest request = ServletActionContext.getRequest();
		String cid=request.getParameter("cid");
		String curPage1=request.getParameter("curPage");
		String pageSize1=request.getParameter("pageSize");
		
		
		int pageSize=Integer.parseInt(pageSize1);
		int curPage=Integer.parseInt(curPage1);
		
		ProductService service=new ProductServiceImpl();
		
		PageBean<Product> pageBean=service.findByCid(cid,curPage,pageSize);
		
		request.setAttribute("pageBean", pageBean);
		
		
		
		return "findByPageSuccess";
		
		
	}
	
	/**查看商品详情
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String showDetail() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String pid=request.getParameter("pid");
		
		ProductService service=new ProductServiceImpl();
		Product product=service.findByPid(pid);
		int pageSize=4;
		CommentService s=(CommentService) BeansFactory.getBean("CommentService");
		
		List<Comment> comments=s.findComment(pid);
		System.out.println(comments);
		request.setAttribute("comms", comments);
		request.setAttribute("p", product);
		
		
		return "showDetailSuccess";
	}
	
}
