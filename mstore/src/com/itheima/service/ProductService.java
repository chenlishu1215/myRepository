package com.itheima.service;

import java.util.List;

import com.itheima.bean.Category;
import com.itheima.bean.Comment;
import com.itheima.bean.PageBean;
import com.itheima.bean.Product;

public interface ProductService<T> {

	

	String findAll();

	List<Product> findHotProduct(int i);

	List<Product> findNewProduct(int i);

	PageBean<T> findByCid(String cid, int curPage,int pageSize);

	Product findByPid(String pid);

	
	
	 List<Category> getCategory()throws Exception;

	PageBean<Product> findProductByPage(int curPage, int pageSize)throws Exception;

	void deleteProduct(String pid)throws Exception;

	void saveProduct(Product product);

	void update(Product product)throws Exception;

	
}
