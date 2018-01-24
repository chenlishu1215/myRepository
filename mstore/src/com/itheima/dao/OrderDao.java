package com.itheima.dao;

import java.sql.SQLException;
import java.util.List;

import com.itheima.bean.Order;
import com.itheima.bean.OrderItem;

public interface OrderDao {

	

	void saveOders(Order order)throws Exception;

	void saveOrderItems(OrderItem item)throws Exception;

	int getTotalRecord(String uid) throws SQLException;

	List<Order> findByPage(String uid, int startIndex, int pageSize)throws Exception;

	Order findOrder(String oid)throws Exception;

	void updateOrder(Order order) throws Exception;

	void updateOrderState(int state, String r6_Order)throws Exception;

	List<Order> findOrderByPage(String state, int startIndex, int pageSize)throws Exception;

	int getOrderTotalRecord(String state)throws Exception;
	
	

}
