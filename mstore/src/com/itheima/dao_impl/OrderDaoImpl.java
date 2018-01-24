package com.itheima.dao_impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.management.Query;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.w3c.dom.ls.LSInput;

import com.itheima.bean.Order;
import com.itheima.bean.OrderItem;
import com.itheima.bean.Product;
import com.itheima.constant.Constant;
import com.itheima.dao.OrderDao;
import com.itheima.utils.C3P0Util;
import com.itheima.utils.TransactionManager;
import com.sun.org.apache.bcel.internal.generic.NEW;

import redis.clients.jedis.Transaction;

public class OrderDaoImpl implements OrderDao {

	/* (non-Javadoc)保存订单信息
	 * @see com.itheima.dao.OrderDao#saveOders(com.itheima.bean.Order)
	 */
	@Override
	public void saveOders(Order order) throws Exception {
		QueryRunner runner =new QueryRunner();
		String sql = "insert into orders values(?,?,?,?, ?,?,?,?)";
		Object [] params={order.getOid(),order.getOrdertime(),order.getTotal(),
							order.getState(),order.getAddress(),order.getName(),
							order.getTelephone(),order.getUid()};
		
		runner.update(TransactionManager.getConnection(),sql,params);
		
	}

	/* (non-Javadoc)保存订单项信息
	 * @see com.itheima.dao.OrderDao#saveOrderItems(com.itheima.bean.OrderItem)
	 */
	@Override
	public void saveOrderItems(OrderItem item) throws Exception {
		QueryRunner runner =new QueryRunner();
		String sql = "insert into orderitem values(?,?,?,?,?)";
		Object [] params={item.getItemid(),item.getCount(),item.getSubtotal(),
							item.getPid(),item.getOid()};
		
		runner.update(TransactionManager.getConnection(),sql,params);
		
	}

	/* (non-Javadoc)查找总记录数
	 * @see com.itheima.dao.OrderDao#getTotalRecord(java.lang.String)
	 */
	@Override
	public int getTotalRecord(String uid) throws SQLException {
		QueryRunner runner=new QueryRunner(C3P0Util.getDataSource());
		String sql= "select count(*) from orders where uid=?";
		Long count =(Long) runner.query(sql, new ScalarHandler(),uid);
		return count.intValue();
	}

	/* (non-Javadoc)分页查找用户的订单 
	 * @see com.itheima.dao.OrderDao#findByPage(java.lang.String, int, int)
	 */
	@Override
	public List<Order> findByPage(String uid, int startIndex, int pageSize) throws Exception {
		QueryRunner runner=new QueryRunner(C3P0Util.getDataSource());
		String sql ="select * from orders where uid=? limit ?,?";
		List<Order> orders = runner.query(sql, new BeanListHandler<Order>(Order.class),uid,startIndex,pageSize);
		
		for (Order order : orders) {
			String sql2 ="select * from product p,orderitem o where p.pid=o.pid and o.oid=?";
			List<Map<String, Object>> maps = runner.query(sql2, new MapListHandler(),order.getOid());
			
			 List<OrderItem> orderItems=new ArrayList<OrderItem>();
			for (Map<String, Object> map : maps) {
							
				OrderItem item = new OrderItem();
				BeanUtils.populate(item, map);
				
				Product product =new Product();
				BeanUtils.populate(product, map);
				
				item.setProduct(product);
				orderItems.add(item);
				
			}
			order.setOrderItems(orderItems);
		}
		return orders;
	}

	/* (non-Javadoc)查找用户的单个订单
	 * @see com.itheima.dao.OrderDao#findOrder(java.lang.String)
	 */
	@Override
	public Order findOrder(String oid) throws Exception {
		QueryRunner runner=new QueryRunner(C3P0Util.getDataSource());
		String sql ="select * from orders where oid=?";
		
		Order order = runner.query(sql, new BeanHandler<Order>(Order.class),oid);
		
		String sql2="select * from orderitem o,product p where o.pid=p.pid and o.oid=?";
		List<Map<String, Object>> maps = runner.query(sql2, new MapListHandler(),oid);
		
		List<OrderItem> items=new ArrayList<OrderItem>();
		for(Map<String, Object> map :maps){
			//封装订单项
			OrderItem item = new OrderItem();
			BeanUtils.populate(item, map);
			//封装商品
			Product product =new Product();
			BeanUtils.populate(product, map);
			//把封装好的商品对象封装到订单详情中
			item.setProduct(product);
			//把订单项封装到订单中
			items.add(item);

		}
		order.setOrderItems(items);
		
		return order;
	}

	@Override
	public void updateOrder(Order order) throws Exception {
		QueryRunner runner=new QueryRunner();
		String sql ="update orders set address=?,name=?,telephone=? where oid=?";
		runner.update(TransactionManager.getConnection(),sql,order.getAddress(),order.getName(),order.getTelephone(),order.getOid());
		
	}

	/* (non-Javadoc)付款成功更改订单状态 
	 * @see com.itheima.dao.OrderDao#updateOrderState(java.lang.String)
	 */
	@Override
	public void updateOrderState(int state, String r6_Order) throws Exception {
		QueryRunner runner=new QueryRunner(C3P0Util.getDataSource());
		String sql="update orders set state=? where oid=?";
		//r6_Order为订单编号 oid
		runner.update(sql,state, r6_Order);
		
	}

	@Override
	public List<Order> findOrderByPage(String state, int startIndex, int pageSize) throws Exception {
		QueryRunner runner=new QueryRunner(C3P0Util.getDataSource());
		String sql =  "select * from orders where 1=1 ";
		List<Object> list=new ArrayList<>();
		
		if(state!=null && !"".equals(state)){
			sql+="and state=? ";
			list.add(Integer.parseInt(state));
		}
		sql+="limit ?,?";
		list.add(startIndex);
		list.add(pageSize);
		List<Order> orders=new ArrayList<>();
		orders=runner.query(sql, new BeanListHandler<Order>(Order.class),list.toArray());
		if(orders.size()!=0){
			for (Order order : orders) {
				String sql2="select * from orderitem o,product p where o.pid=p.pid and oid=?";
				List<Map<String, Object>> maps = runner.query(sql2, new MapListHandler(),order.getOid());
				List<OrderItem> items=new ArrayList<>();
				for(Map<String, Object> map:maps){
					OrderItem item=new OrderItem();
					BeanUtils.populate(item, map);
					
					Product product=new Product();
					BeanUtils.populate(product, map);
					
					item.setProduct(product);
					items.add(item);
				}
				order.setOrderItems(items);
			}
			
		}
		return orders;
	}

	@Override
	public int getOrderTotalRecord(String state) throws Exception {
		QueryRunner runner=new QueryRunner(C3P0Util.getDataSource());
		String sql = "select count(*) from orders where 1=1 ";
		List list=new ArrayList<>();
		if(state!=null && !"".equals(state)){
			int state1 = Integer.parseInt(state);
			sql+="and state=?";
			list.add(state1);
		}
		Long count=0l;
		try {
			count=(Long) runner.query(sql, new ScalarHandler(),list.toArray());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count.intValue();
	}

	

	

}
