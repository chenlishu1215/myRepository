package com.itheima.service;



import com.itheima.bean.Order;
import com.itheima.bean.PageBean;

public interface OrderService {

	void save(Order order) throws Exception ;

	PageBean<Order> findByPage(String uid, int curPage, int pageSize) throws Exception;

	Order findOrder(String oid)throws Exception;

	void updateOrder(Order order) throws Exception;

	void updateOrderState(int state, String r6_Order)throws Exception;

	PageBean<Order> findOrderByPage(String state, int curPage, int pageSize) throws Exception;



}
