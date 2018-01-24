package com.itheima.dao_impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.itheima.bean.Category;
import com.itheima.bean.Comment;
import com.itheima.bean.Product;
import com.itheima.dao.ProductDao;
import com.itheima.utils.C3P0Util;
import com.itheima.utils.HibernateUtils;


public class ProductDaoImpl implements ProductDao {

	
	//查询所有的分类
	@Override
	public List<Category> findAllCategory() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Category> list = session.createCriteria(Category.class).list();
		return list;
	}
	//查询最热商品
	@Override
	public List<Product> findHotProduct(int i) {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Product> list = session.createCriteria(Product.class).add(Restrictions.eq("is_hot", 1)).setFirstResult(0).setMaxResults(i).list();
		int count=list.size();
		transaction.commit();
		return list;
	}
	//查询最新商品
	@Override
	public List<Product> findNewProduct(int i) {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		List<Product> list = session.createCriteria(Product.class).add(Restrictions.eq("is_hot", 1)).setFirstResult(0).setMaxResults(i).addOrder(Order.desc("pdate")).list();
		int count=list.size();
		transaction.commit();
		return list;
		
		
	}
	//根据cid 查询商品信息
	@Override
	public List<Product> findByCid(String cid,int index,int pageSize) {
		
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Criteria criteria = session.createCriteria(Product.class);
		List<Product> list = criteria.add(Restrictions.eq("category.cid", cid)).setFirstResult(index).setMaxResults(pageSize).list();
		int size=list.size();
		transaction.commit();
				
		return list;
	}
	
	
	@Override
	public int queryTotalRecord(String cid) {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
	
		Criteria criteria = session.createCriteria(Product.class);
		Long count = (Long) criteria.setProjection(Projections.rowCount()).add(Restrictions.eq("category.cid", cid)).uniqueResult();
		int intValue = count.intValue();
		
		transaction.commit();
		
		return intValue;
	}
	@Override
	public Product findByPid(String pid) {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
	
		Criteria criteria = session.createCriteria(Product.class);
		Product product =(Product) criteria.add(Restrictions.eq("pid", pid)).uniqueResult();
		product.getCategory().getCname();
		transaction.commit();
		return product;
	}
	@Override
	public Category findCategory(String cid) {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
	
		Criteria criteria = session.createCriteria(Category.class);
		Category category =(Category) criteria.add(Restrictions.eq("cid", cid)).uniqueResult();
		transaction.commit();
		return category;
	}
	
	
	@Override
	public int findProductCount() {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
	
		Criteria criteria = session.createCriteria(Product.class);
		Long count=(Long) criteria.setProjection(Projections.count("pid")).uniqueResult();
		transaction.commit();
		return count.intValue();
	}
	@Override
	public List findProductByPage(int startIndex, int pageSize) {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
	
		Criteria criteria = session.createCriteria(Product.class);
		List<Product> list = criteria.setFirstResult(startIndex).setMaxResults(pageSize).list();
		transaction.commit();
		return list;
	}
	@Override
	public void deleteProduct(String pid) {
		QueryRunner runner=new QueryRunner(C3P0Util.getDataSource());
		String sql= "delete from product where pid=?";
		try {
			runner.update(sql,pid);
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	@Override
	public void saveProduct(Product p) {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Category category = session.get(Category.class, p.getPid());
		System.out.println("CID"+p.getCid());
		category.getProducts().add(p);
		p.setCategory(category);
		session.save(category);
		
		
		transaction.commit();
	}
	@Override
	public void update(Product p) {
		Session session = HibernateUtils.getCurrentSession();
		Transaction transaction = session.beginTransaction();
		Product product = session.get(Product.class, p.getPid());
		/*{p.getPid(),p.getPname(),p.getMarket_price(),
						p.getShop_price(),p.getPimage(),p.getPdate(),
						p.getIs_hot(),p.getPdesc(),p.getPflag(),
						p.getCid()};
		 * */
		product.setPname(p.getPname());
		product.setMarket_price(p.getMarket_price());
		product.setShop_price(p.getShop_price());
		product.setPimage(p.getPimage());
		product.setPdate(p.getPdate());
		product.setIs_hot(p.getIs_hot());
		product.setPdesc(p.getPdesc());
		product.setPflag(p.getPflag());
	
	
		transaction.commit();
		
		
	}
	
	
	

}
