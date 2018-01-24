package com.itheima.bean;

import java.util.Date;
import java.util.List;

public class Order {
	//用来封装订单 
	private List<OrderItem> orderItems;
	
	private String oid;
	private Date ordertime;
	private double total;
	private int state;
	private String address;
	private String name;
	private String telephone;
	private String uid;
	
	private User user;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems=orderItems;
	}
	public String getOid() {
		return oid;
	}
	public void setOid(String oid) {
		this.oid = oid;
	}
	public Date getOrdertime() {
		return ordertime;
	}
	public void setOrdertime(Date date) {
		this.ordertime = date;
	}
	public double getTotal() {
		if(total==0.0){
			for(OrderItem item:orderItems){
				total+=item.getSubtotal();
			}
		}
		return total;
	}
	
	public int getState() {
		return state;
	}
	public void setState(int state) {
		this.state = state;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void setTotal(double total) {
		this.total=total;
		
	}
	
	
	
}
