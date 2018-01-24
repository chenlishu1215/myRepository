package com.itheima.dao;

import com.itheima.bean.Category;

public interface CategoryDao {

	void delCategory(String cid);

	Category categoryBackShow(String cid);

	void updateCategory(Category c);

	void addCategory(String cid, String cname);

	

}
