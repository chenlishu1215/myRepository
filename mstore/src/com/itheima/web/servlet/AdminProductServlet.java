package com.itheima.web.servlet;



import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;

import com.itheima.bean.PageBean;
import com.itheima.bean.Product;
import com.itheima.constant.Constant;
import com.itheima.service.ProductService;
import com.itheima.service_impl.ProductServiceImpl;
import com.itheima.utils.DateUtils;
import com.itheima.utils.UUIDUtils;
import com.itheima.utils.UploadUtils;
import com.opensymphony.xwork2.ActionSupport;
import com.sun.mail.imap.protocol.Item;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * 
 */
public class AdminProductServlet extends ActionSupport {
	

	
	public String  findAllProduct() throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		int curPage = Integer.parseInt(request.getParameter("curPage"));
		ProductService service=new ProductServiceImpl();
		int pageSize=10;
		PageBean<Product> pageBean=service.findProductByPage(curPage,pageSize);
		request.setAttribute("pageBean", pageBean);
		
		return "findAllProductSuccess";
				
	}
	public String adminUI() throws Exception {
		//假装在这里做了权限操作
		
		
		return "adminUISuccess";
		
	}
	
	public String deleteProduct() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String pid = request.getParameter("pid");
		System.out.println(pid);
		ProductService service=new ProductServiceImpl();
		//service.deleteProduct(pid);
		request.setAttribute("state", "0");
		
		return "dispatcherFindAllProductSuccess";
		
	}
	public String productBackShow() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		String pid=request.getParameter("pid");
		ProductService service=new ProductServiceImpl();
		Product p = service.findByPid(pid);
		request.setAttribute("p", p);
		
		return "productBackShowSuccess";
		
	}
	public String addProduct() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		//创建 文件上传核心工厂
		DiskFileItemFactory factory=new DiskFileItemFactory();
		//设置缓存文件的存放路径
		//factory.setRepository(new File("D:/"));
		//设置缓存区的大小，如果上传的文件超出了这个大小，就会生成配置文件
		//factory.setSizeThreshold(1*1024*1024);
		
		//创建文件上传的核心解析类
		ServletFileUpload upload=new ServletFileUpload(factory);
		
		//解析request请求
		List <FileItem> fileItems = upload.parseRequest(request);
		
		
		Map<String, Object> map=new HashMap<>();
		//遍历List中每一个文件
		for (FileItem fileItem : fileItems) {
			//获取属性名
			String name = fileItem.getFieldName();
			//如果是普通表单项
			if(fileItem.isFormField()){
				//获取属性值
				String value=fileItem.getString("UTF-8");
				System.out.println( name+"：：：：：：：：：：："+value);
				map.put(name,value );
			}else{
				//获取文件名
				String fileName = fileItem.getName();
				String realName = UploadUtils.getRealName(fileName);
				String dir = UploadUtils.getDir();
				String uuidName = UploadUtils.getUUIDName(realName);
				String realPath = request.getServletContext().getRealPath("products");
				String pImageRealPath=realPath+dir+"/"+uuidName;
				map.put("pimage","products"+dir+"/"+uuidName);
				
				InputStream is = fileItem.getInputStream();
				File file = new File(realPath+dir);
				System.out.println(file);
				if(!file.exists()){
					file.mkdirs();
				}
				FileOutputStream fos=new FileOutputStream(new File(pImageRealPath));
				//System.out.println(pImageRealPath);
				IOUtils.copy(is, fos);
				IOUtils.closeQuietly(is);
				IOUtils.closeQuietly(fos);
				fileItem.delete();
				
			}
		}
		Product product=new Product();
		BeanUtils.populate(product, map);
		
		product.setPid(UUIDUtils.getId());
		product.setPflag(Constant.UP);
		product.setPdate(DateUtils.getLessDate());
		ProductService service=new ProductServiceImpl();
		//service.saveProduct(product);
		System.out.println("product值为："+product);
		return "dispatcherFindAllProductSuccess";
		
	}
	
	public String editProduct() throws Exception {
		HttpServletRequest request = ServletActionContext.getRequest();
		DiskFileItemFactory factory=new DiskFileItemFactory();
		//factory.setRepository(new File("D:/"));
		//factory.setSizeThreshold(1*1024*1024);
		ServletFileUpload upload=new ServletFileUpload(factory);
		List<FileItem> fileItems = upload.parseRequest(request);
		Map<String, Object> map=new HashMap<>();
		boolean flag=true;
		
		for (FileItem fileItem : fileItems) {
			
			if(fileItem.isFormField()){
				map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
				
			}else{
				
				String name = fileItem.getName();
				if(!"".equals(name)){
					flag=false;
					String realName = UploadUtils.getRealName(name);
					String dir = UploadUtils.getDir();
					String realPath = request.getServletContext().getRealPath("products");
					map.put("pimage", "products"+dir+"/"+realName);
					File file = new File(realPath+dir);
					if(!file.exists()){
						file.mkdirs();
					}
					InputStream is = fileItem.getInputStream();
					FileOutputStream fos = new FileOutputStream(new File(realPath+dir+"/"+realName));
					IOUtils.copy(is, fos);
					IOUtils.closeQuietly(is);
					IOUtils.closeQuietly(fos);
					fileItem.delete();
				}
			}
		}
	
		map.put("pid", request.getParameter("pid"));
		if(flag){
			
			
			map.put("pimage", request.getParameter("pimage"));
		}else{
			String path = request.getParameter("pimage");
			String realPath = request.getServletContext().getRealPath("products");
			realPath=realPath+path.substring(8);
			
			new File(realPath).delete();
		}
		
		Product product=new Product();
		BeanUtils.populate(product, map);
		product.setPdate(DateUtils.getLessDate());
		
		
		ProductService service=new ProductServiceImpl();
		service.update(product);
		return "dispatcherFindAllProductSuccess";
		
	}
	
}
