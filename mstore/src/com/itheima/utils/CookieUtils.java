package com.itheima.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {
		
	public static void createCookie(HttpServletResponse response,String name,String value,int secoend,String path) throws UnsupportedEncodingException{
		//对value进行编码
		value=URLEncoder.encode(value,"UTF-8");
		//创建对象 并设置值
		Cookie cookie=new Cookie(name, value);
		//设置有效期
		cookie.setMaxAge(secoend);
		//设置cookie的有效范围
		cookie.setPath(path);
		
		response.addCookie(cookie);
	}
	
	public static String getCookie(HttpServletRequest request,String name) throws UnsupportedEncodingException{
		Cookie[] cookies = request.getCookies();
		String value=null;
		if(cookies!=null){
			for(Cookie cookie:cookies){
				if(name.equals(cookie.getName())){
					value=cookie.getValue();
					value=URLDecoder.decode(value,"UTF-8");

				}
			}
		}
				return value;
	}
	
	
	
}
