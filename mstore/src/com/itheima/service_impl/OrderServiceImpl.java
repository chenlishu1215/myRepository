package com.itheima.service_impl;







import com.itheima.bean.Order;
import com.itheima.bean.OrderItem;
import com.itheima.bean.PageBean;
import com.itheima.dao.OrderDao;
import com.itheima.dao_impl.OrderDaoImpl;
import com.itheima.service.OrderService;
import com.itheima.utils.TransactionManager;






public class OrderServiceImpl implements OrderService {

	@Override
	public void save(Order order) throws Exception  {
		try {
			TransactionManager.startTransaction();
			
			OrderDao dao=new OrderDaoImpl();
			
			dao.saveOders(order);
			
			for(OrderItem item:order.getOrderItems()){
				
				dao.saveOrderItems(item);
			}
			
			TransactionManager.commit();
		} catch (Exception e) {
		
			e.printStackTrace();
			TransactionManager.rollback();
			throw new Exception("订单保存失败！");
		}
		
	}

	@Override
	public PageBean<Order> findByPage(String uid, int curPage, int pageSize) throws Exception {
		OrderDao dao=new OrderDaoImpl();
		PageBean<Order> pageBean =new PageBean();
		pageBean.setPageSize(pageSize);
		pageBean.setCurPage(curPage);
		pageBean.setTotalRecord(dao.getTotalRecord(uid));
		pageBean.setData(dao.findByPage(uid,pageBean.getStartIndex(),pageSize));
		
		return pageBean;
	}

	@Override
	public Order findOrder(String oid) throws Exception {
		OrderDao dao=new OrderDaoImpl();
		
		return dao.findOrder(oid);
	}

	@Override
	public void updateOrder(Order order)throws Exception {
		try {
			TransactionManager.startTransaction();
			
			OrderDao dao=new OrderDaoImpl();
			dao.updateOrder(order);
			
			TransactionManager.commit();
		} catch (Exception e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			TransactionManager.rollback();
			throw new Exception("表单更新失败！");
		}
		
		
		
	}

	@Override
	public void updateOrderState( int state, String r6_Order) throws Exception {
		 OrderDao dao=new OrderDaoImpl();
		 dao.updateOrderState(state ,r6_Order);
		
	}
	@Override
	public PageBean<Order> findOrderByPage(String state, int curPage,int pageSize) throws Exception {
		OrderDao dao=new OrderDaoImpl();
		PageBean<Order> pageBean =new PageBean();
		pageBean.setPageSize(pageSize);
		pageBean.setCurPage(curPage);
		pageBean.setTotalRecord(dao.getOrderTotalRecord(state));
		pageBean.setData(dao.findOrderByPage(state,pageBean.getStartIndex(),pageSize));
		
		return pageBean;
	}

	

}
