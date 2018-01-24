package com.itheima.bean;

import java.util.Collection;
import java.util.HashMap;

import com.itheima.service.ProductService;
import com.itheima.service_impl.ProductServiceImpl;



public class Cart {
	private HashMap<String,CartItems> maps=new HashMap<String,CartItems>();
	private double total;
	public Collection<CartItems> getCartItems(){
		//将map的值 取出来存到集合
		return maps.values();
	}
	
	public double getTotal() {
		//每次进来重新赋值为0
		total=0d;
		for (CartItems  item : getCartItems()) {
			total+=item.getSubTotal();
			
		}
		return total;
	}
	public void setMaps(HashMap<String, CartItems> maps) {
		this.maps = maps;
	}
	
	public HashMap<String, CartItems> getMaps() {
		return maps;
	}
	//添加商品
	public void addProduct(String pid,int count){
		
		CartItems cartItems = new CartItems();
		cartItems.setCount(count);
		ProductService service=new ProductServiceImpl();
		Product product = service.findByPid(pid);
		cartItems.setProduct(product);
		maps.put(pid, cartItems);
		
	}
	//删除商品
	public void deleteProduct(String pid){
		maps.remove(pid);
	}
	//清空商品（购物车）
	public void clearCart(){
		maps.clear();
	}
	
	
}
