package com.itheima.service_impl;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.itheima.bean.Category;
import com.itheima.bean.PageBean;
import com.itheima.bean.Product;
import com.itheima.constant.Constant;
import com.itheima.dao.ProductDao;
import com.itheima.dao_impl.ProductDaoImpl;
import com.itheima.service.ProductService;
import com.itheima.utils.JedisUtils;

import redis.clients.jedis.Jedis;

public class ProductServiceImpl implements ProductService {

	
	

	/* (non-Javadoc)查找所有商品的分类信息
	 * @see com.itheima.service.ProductService#findAll()
	 */
	@Override
	public String findAll() {
		
		
		Jedis jedis=null;
		List<Category> list=new ArrayList<>();
		try {
			jedis=JedisUtils.getJedis();
			String value = jedis.get(Constant.CATEGORY_ALL);
			//如果jedis获取的值不为空  直接返回
			if(value!=null){
				
				
				return value;
				
			}
			
			 list= getCategory();
			 
			 for (Category category : list) {
				 category.setProducts(null);
			 }
			 value = new Gson().toJson(list);
			jedis.set(Constant.CATEGORY_ALL, value);
			
			
			return value;
		} catch (Exception e) {
			
			 list = getCategory();
			 for (Category category : list) {
					category.setProducts(null);
				}
			String json = new Gson().toJson(list);
			
			System.out.println("出现异常，从mysql中获取所需数据");
			
			e.printStackTrace();
			return json;
		}finally{
			if(jedis!=null){
				jedis.close();
			}
		}
			
	}
	/**提供给fandAll调用的方法
	 * @return
	 */
	public List<Category> getCategory(){
		ProductDao dao=new ProductDaoImpl();
		List<Category> list=dao.findAllCategory();
		return list;
	}
	@Override
	public List<Product> findHotProduct(int i) {
		ProductDao dao=new ProductDaoImpl();
		return dao.findHotProduct(i);
	}
	@Override
	public List<Product> findNewProduct(int i) {
		ProductDao dao=new ProductDaoImpl();
		return dao.findNewProduct(i);
		
	}
	@Override
	public PageBean<Product>findByCid(String cid,int curPage,int pageSize) {
		ProductDao dao=new ProductDaoImpl();
		PageBean <Product> pageBean=new PageBean();
		//设置数据
		pageBean.setCurPage(curPage);
		
		pageBean.setPageSize(pageSize);

		//查询总数据条数
		int count =dao.queryTotalRecord(cid);
		pageBean.setTotalRecord(count);
		//根据总数据条数和页面显示数据条数 计算出总页数
		int totalPage=(int) Math.ceil(count*1.0/pageSize);
		//设置数据
		pageBean.setTotalPage(totalPage);
		List<Product> products=dao.findByCid(cid,pageBean.getStartIndex(),pageSize);
		 pageBean.setData(products);
		 
		 
		 return pageBean;
	}
	/* (non-Javadoc)根据商品分类查找分类信息
	 * @see com.itheima.service.ProductService#findByPid(java.lang.String)
	 */
	@Override
	public Product findByPid(String pid) {
		ProductDao dao=new ProductDaoImpl();
		Product product=dao.findByPid(pid);
		
//		product.setCategory(dao.findCategory(product.getCid()));
		return product;
	}
	
	@Override
	public PageBean findProductByPage(int curPage, int pageSize) throws Exception {
		ProductDao dao=new ProductDaoImpl();
		PageBean pageBean=new PageBean();
		pageBean.setCurPage(curPage);
		pageBean.setPageSize(pageSize);
		pageBean.setTotalRecord(dao.findProductCount());
		pageBean.setData(dao.findProductByPage(pageBean.getStartIndex(),pageSize));
		
		
		return pageBean;
	}
	@Override
	public void deleteProduct(String pid) throws Exception {
		ProductDao dao=new ProductDaoImpl();
		dao.deleteProduct(pid);
		
	}
	@Override
	public void saveProduct(Product product) {
		ProductDao dao=new ProductDaoImpl();
		dao.saveProduct(product);
	}
	@Override
	public void update(Product p) throws Exception {
		ProductDao dao=new ProductDaoImpl();
		dao.update(p);
		
	}

}
