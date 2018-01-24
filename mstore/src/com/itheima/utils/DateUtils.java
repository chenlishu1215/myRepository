package com.itheima.utils;

import java.text.SimpleDateFormat;

import java.util.Date;



public class DateUtils {
	
	public static String getDate(){
		SimpleDateFormat formart=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String  time= formart.format(new Date());
		return time;
	}
	public static String getLessDate(){
		SimpleDateFormat formart=new SimpleDateFormat("yyyy-MM-dd");
		String  time= formart.format(new Date());
		return time;
	}
	
	
	
}
