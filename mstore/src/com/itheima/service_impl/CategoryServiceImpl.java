package com.itheima.service_impl;

import com.itheima.bean.Category;
import com.itheima.dao.CategoryDao;
import com.itheima.dao_impl.CategoryDaoImpl;
import com.itheima.service.CategoryService;

public class CategoryServiceImpl implements CategoryService {

	@Override
	public void delCategory(String cid) throws Exception {
		CategoryDao dao=new CategoryDaoImpl();
		
		dao.delCategory(cid);
	}

	@Override
	public Category categoryBackShow(String cid) throws Exception {
		CategoryDao dao = new CategoryDaoImpl();

		return dao.categoryBackShow(cid);
		
	}

	@Override
	public void updateCategory(Category c) throws Exception {
		CategoryDao dao = new CategoryDaoImpl();
		if(c.getCname()==null&c.getCname().trim()==""){
			return;
		}
		dao.updateCategory(c);
	}

	@Override
	public void addCategory(String cid, String cname) throws Exception {
		CategoryDao dao = new CategoryDaoImpl();
		dao.addCategory(cid,cname);
		
	}
	
}
