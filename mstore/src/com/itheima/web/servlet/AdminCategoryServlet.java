package com.itheima.web.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts2.ServletActionContext;

import com.itheima.bean.Category;
import com.itheima.constant.Constant;
import com.itheima.service.CategoryService;
import com.itheima.service.ProductService;
import com.itheima.service_impl.CategoryServiceImpl;
import com.itheima.service_impl.ProductServiceImpl;
import com.itheima.utils.JedisUtils;
import com.itheima.utils.UUIDUtils;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Servlet implementation class AdminCategoryServlet
 */
public class AdminCategoryServlet extends ActionSupport {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	public  String  adminUI() throws ServletException, IOException {
		//假装这里做了权限的处理
		
		//response.sendRedirect(request.getContextPath()+"/admin/category/add.jsp");
		return "adminUISuccess";
			
		
	}
	//查找所有的分类
	public String findAll() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		ProductService service=new ProductServiceImpl();
		List<Category> categorys = service.getCategory();
		
		request.setAttribute("categorys", categorys);
		return "findAllSuccess";
		
		 
	}
	/*public String findAllCategory(HttpServletRequest request) throws Exception {
		ProductService service=new ProductServiceImpl();
		List<Category> categorys = service.getCategory();
		
		request.setAttribute("categorys", categorys);
		return "/admin/category/list.jsp";
	}*/
	public String categoryBackShow() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String cid = request.getParameter("cid");
		CategoryService s=new CategoryServiceImpl();
		Category category=s.categoryBackShow(cid);
		request.setAttribute("category", category);
		return "categoryBackShowSuccess";
		
	}
	public String delCategory() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String cid = request.getParameter("cid");
		CategoryService s=new CategoryServiceImpl();
		s.delCategory(cid);
		JedisUtils.delJedis(Constant.CATEGORY_ALL);
		
		return "responseFindAllSuccess";
	}
	public String editCategory () throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		Map<String, String[]> map = request.getParameterMap();
		Category c=new Category();
		BeanUtils.populate(c, map);
		CategoryService s=new CategoryServiceImpl();
		s.updateCategory(c);
		JedisUtils.delJedis(Constant.CATEGORY_ALL);
		
		return "responseFindAllSuccess";
		
	}
	public String addCategory () throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String cname=request.getParameter("cname");
		String cid=UUIDUtils.getId();
		CategoryService s=new CategoryServiceImpl();
		s.addCategory(cid,cname);
		JedisUtils.delJedis(Constant.CATEGORY_ALL);
		
		return "responseFindAllSuccess";
		
	}
}
