package com.itheima.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.itheima.bean.Cart;
import com.itheima.bean.CartItems;
import com.itheima.bean.Order;
import com.itheima.bean.OrderItem;
import com.itheima.bean.PageBean;
import com.itheima.bean.Product;
import com.itheima.bean.User;
import com.itheima.constant.Constant;
import com.itheima.service.OrderService;
import com.itheima.service.ProductService;
import com.itheima.service_impl.OrderServiceImpl;
import com.itheima.service_impl.ProductServiceImpl;
import com.itheima.utils.PaymentUtil;
import com.itheima.utils.UUIDUtils;
import com.opensymphony.xwork2.ActionSupport;

/**
 * Servlet implementation class OrderServlet
 */
public class OrderServlet extends ActionSupport {
	private static final long serialVersionUID = 1L;

	/**从购物车页面 进行提交的操作
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	public  String  orderSubmit() throws ServletException, IOException {
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute("user");
		if(user==null){
			
			request.setAttribute("msg", "您还未登录，请登录后购买！");
			return "failed";
		}
		
		try {
			HttpSession session= request.getSession();
			Cart cart=(Cart)session.getAttribute("cart");
			
			Order order = new Order();
			String oid=UUIDUtils.getId();
			order.setOid(oid);
			order.setOrdertime(new Date( ));
			order.setTotal(cart.getTotal());
			order.setState(Constant.UNPAY);
			order.setUid(user.getUid());
			
			List<OrderItem> orderItems=new ArrayList<>();
			//将cart中的商品详情 封装到orderItem对象中
			for(CartItems item :cart.getCartItems()){
				OrderItem orderItem=new OrderItem();
				orderItem.setItemid(UUIDUtils.getId());
				orderItem.setCount(item.getCount());
				orderItem.setSubtotal(item.getSubTotal());
				orderItem.setPid(item.getProduct().getPid());
				orderItem.setOid(oid);
				orderItem.setProduct(item.getProduct());
				//把封装好的orderItem对象 添加到集合中
				orderItems.add(orderItem);
				
			}
			//将集合封装到order对象中 
			order.setOrderItems(orderItems);
			//调用service层 保存订单 订单项信息
			OrderService service=new OrderServiceImpl();
			
			service.save(order);
			
			//将订单信息存入域中  
			request.setAttribute("order", order);
			
			//清空购物车
			cart.clearCart();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return "success";
	}
	
	/**查看订单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findByPage() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		int curPage=Integer.parseInt(request.getParameter("curPage"));
		int pageSize=2;
		HttpSession session = request.getSession();
		User user = (User) session.getAttribute("user");
		String uid=user.getUid();
		OrderService service=new OrderServiceImpl();
		
		PageBean<Order> pageBean=service.findByPage(uid,curPage,pageSize);
		request.setAttribute("pageBean",pageBean);
		
	
		
		return "findByPageSuccess";
		
	}
	
	/**从订单页面去付款
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String orderSubmit2() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		String oid = request.getParameter("oid");
		OrderService service=new OrderServiceImpl();
		Order order=service.findOrder(oid);
		request.setAttribute("order", order);
		
		return "success";
	
	}
	public String buy()throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		User user = (User) request.getSession().getAttribute("user");
		if(user==null){
			
			request.setAttribute("msg", "您还未登录，请登录后购买！");
			return "failed";
		}
		
		String pid = request.getParameter("pid");
		int count =Integer.parseInt(request.getParameter("count"));
		
		Order order=new Order();
		order.setOid(UUIDUtils.getId());
		order.setOrdertime(new Date());
		order.setState(Constant.UNPAY);
		order.setUid(user.getUid());
		
		List<OrderItem> items =new ArrayList<OrderItem>();
		ProductService service =new ProductServiceImpl();
		OrderItem item=new OrderItem();
		item.setItemid(UUIDUtils.getId());
		item.setOid(order.getOid());
		item.setPid(pid);
		item.setProduct(service.findByPid(pid));
		item.setCount(count);
		items.add(item);
		order.setOrderItems(items);
		
		OrderService s=new  OrderServiceImpl();
		
		s.save(order);
		request.setAttribute("order",order);
		return "success";
		
	}
	
	public String pay() throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		String address = request.getParameter("address");
		String name = request.getParameter("name");
		String telephone = request.getParameter("telephone");
		String oid = request.getParameter("oid");
		
		
		OrderService service=new OrderServiceImpl();
		Order order=service.findOrder(oid);
		order.setAddress(address);
		order.setTelephone(telephone);
		order.setName(name);
		order.getOrderItems().clear();
		service.updateOrder(order);
		double total=order.getTotal();
		

		// 组织发送支付公司需要哪些数据
		String pd_FrpId = request.getParameter("pd_FrpId");
		String p0_Cmd = "Buy";
		String p1_MerId = ResourceBundle.getBundle("merchantInfo").getString("p1_MerId");
		String p2_Order = oid;
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		// 支付成功回调地址 ---- 第三方支付公司会访问、用户访问
		// 第三方支付可以访问网址
		String p8_Url = ResourceBundle.getBundle("merchantInfo").getString("responseURL");
		String p9_SAF = "";
		String pa_MP = "";
		String pr_NeedResponse = "1";
		// 加密hmac 需要密钥
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue);
	
		
		//发送给第三方
		StringBuffer sb = new StringBuffer("https://www.yeepay.com/app-merchant-proxy/node?");
		sb.append("p0_Cmd=").append(p0_Cmd).append("&");
		sb.append("p1_MerId=").append(p1_MerId).append("&");
		sb.append("p2_Order=").append(p2_Order).append("&");
		sb.append("p3_Amt=").append(p3_Amt).append("&");
		sb.append("p4_Cur=").append(p4_Cur).append("&");
		sb.append("p5_Pid=").append(p5_Pid).append("&");
		sb.append("p6_Pcat=").append(p6_Pcat).append("&");
		sb.append("p7_Pdesc=").append(p7_Pdesc).append("&");
		sb.append("p8_Url=").append(p8_Url).append("&");
		sb.append("p9_SAF=").append(p9_SAF).append("&");
		sb.append("pa_MP=").append(pa_MP).append("&");
		sb.append("pd_FrpId=").append(pd_FrpId).append("&");
		sb.append("pr_NeedResponse=").append(pr_NeedResponse).append("&");
		sb.append("hmac=").append(hmac);
		
		response.sendRedirect(sb.toString());
		
		
		
		return NONE;
		
	}
	public String callback()throws Exception{
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpServletResponse response = ServletActionContext.getResponse();
		
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");
		String rb_BankId = request.getParameter("rb_BankId");
		String ro_BankOrderId = request.getParameter("ro_BankOrderId");
		String rp_PayDate = request.getParameter("rp_PayDate");
		String rq_CardNo = request.getParameter("rq_CardNo");
		String ru_Trxtime = request.getParameter("ru_Trxtime");
		// 身份校验 --- 判断是不是支付公司通知你
		String hmac = request.getParameter("hmac");
		String keyValue = ResourceBundle.getBundle("merchantInfo").getString(
				"keyValue");

		// 自己对上面数据进行加密 --- 比较支付公司发过来hamc
		boolean isValid = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		if (isValid) {
			// 响应数据有效
			if (r9_BType.equals("1")) {
				// 浏览器重定向
				System.out.println("111");
				request.setAttribute("msg", "您的订单号为:"+r6_Order+",金额为:"+r3_Amt+"已经支付成功,等待发货~~");
				return "/jsp/msg.jsp";
			} else if (r9_BType.equals("2")) {
				// 服务器点对点 --- 支付公司通知你
				System.out.println("付款成功！222");
				// 修改订单状态 为已付款
				// 回复支付公司
				response.getWriter().print("success");
			}
			
			//修改订单状态
			OrderService service=new OrderServiceImpl();
			service.updateOrderState(Constant.UNPAY,r6_Order);
			
		} else {
			// 数据无效
			System.out.println("数据被篡改！");
		}
		
		
		return "msg";
		
	}
	
}
