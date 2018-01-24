package com.itheima.dao;

import java.util.List;

import org.omg.CORBA.INTERNAL;

import com.itheima.bean.Category;
import com.itheima.bean.Comment;
import com.itheima.bean.Product;


public interface ProductDao {



	List<Category> findAllCategory();

	List<Product> findHotProduct(int i);

	List<Product> findNewProduct(int i);

	

	int queryTotalRecord(String cid);

	List<Product> findByCid(String cid, int i, int pAGE_SIZE);

	Product findByPid(String pid);

	Category findCategory(String pid);

	

	int findProductCount();

	List findProductByPage(int startIndex, int pageSize);

	void deleteProduct(String pid);

	void saveProduct(Product product);

	void update(Product p);



}
