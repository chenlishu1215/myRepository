package com.itheima.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.itheima.bean.Comment;
import com.itheima.bean.User;
import com.itheima.service.CommentService;
import com.itheima.utils.BeansFactory;
import com.itheima.utils.DateUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.util.ValueStack;

/**
 * Servlet implementation class CommentServlet
 */
public class CommentServlet extends ActionSupport {
	private Comment comment;

	public Comment getComment() {
		return comment;
	}
	/**
	 * 
	 */
	public String  saveContent() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		
	
		String pid=request.getParameter("pid");
		String content = request.getParameter("content");
		User user=(User) request.getSession().getAttribute("user");
		ActionContext context = ActionContext.getContext();
		ValueStack valueStack = context.getValueStack();
		valueStack.set("pid", pid);
		if(user==null){
			//response.sendRedirect(request.getContextPath()+"/product?method=showDetail&pid="+pid);
			return "saveContentSuccess";
		}
		CommentService s=(CommentService) BeansFactory.getBean("CommentService");
		
		
		
		
		Comment c=new Comment();
		c.setComment(content);
		c.setDate(DateUtils.getDate());
		c.setPid(pid);
		c.setUsername(user.getUsername());
		c.setGood(0);
		s.save(c,user.getUid());
		
		
		
		//response.sendRedirect(request.getContextPath()+"/product?method=showDetail&pid="+pid);
	return "saveContentSuccess";
	}
	public String updateComment() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		
		User user=(User) request.getSession().getAttribute("user");
		if(user==null){
			
			return NONE;
		}
		int cid = Integer.parseInt( request.getParameter("cid"));
		CommentService service=(CommentService) BeansFactory.getBean("CommentService");
		boolean flag = service.checkRecord(cid, user.getUid());
		if(!flag){
			service.updateComment(cid,user.getUid());
			comment=service.findGood(cid);
			System.out.println(comment.getGood());
			
			//response.getWriter().write(new Gson().toJson(comment));
		}
		
		System.out.println("到了这里了！");
		return "tojson";
	
	}

}
