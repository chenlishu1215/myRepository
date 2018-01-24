package com.itheima.utils;



import java.io.InputStream;


import org.dom4j.Document;

import org.dom4j.Element;

import org.dom4j.io.SAXReader;


public class BeansFactory {
	

	public static Object getBean(String id) throws Exception{
		//创建dom4j的核心解析类
		SAXReader reader=new SAXReader();
		//通过类加载器获得一个文件输入流
		InputStream is = BeansFactory.class.getClassLoader().getResourceAsStream("beans.xml");
		//读流 
		Document document = reader.read(is);
		//通过过滤器 获得指定的节点
		Element element = (Element) document.selectSingleNode("//bean[@id='"+id+"']");
		//通过节点获取节点的class属性值
		String className = element.attributeValue("class");
		//通过类的全路径名获得字节码对象
		Class<?> clazz = Class.forName(className);
		
		
		//返回一个实例对象
		return clazz.newInstance();
	}
}
