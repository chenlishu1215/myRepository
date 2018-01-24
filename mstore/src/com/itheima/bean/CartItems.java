package com.itheima.bean;

public class CartItems {
	//
	private Product product;
	private int count;
	//小计
	private double subTotal;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubTotal(){
		return count*product.getShop_price();
	}
	
}
