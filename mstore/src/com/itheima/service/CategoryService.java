package com.itheima.service;

import com.itheima.bean.Category;

public interface CategoryService {

	void delCategory(String cid)throws Exception;

	Category categoryBackShow(String cid)throws Exception;

	void updateCategory(Category c)throws Exception;

	void addCategory(String cid, String cname)throws Exception;

}
