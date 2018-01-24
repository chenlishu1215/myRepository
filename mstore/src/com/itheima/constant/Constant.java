package com.itheima.constant;

public class Constant {
	//商品上下架
	public static final int UP = 0;
	public static final int DOWN = 1;
	//状态码  0 为未激活 1 为已激活
	public  static int ACTIVE=1;
	public  static int UNACTIVE=0;
	//密码需要添加的字符串（盐）
	public static String SALT="E#N*0^3@";
	//cookie的有效期
	public static int second=7*24*60*60;
	//redis中缓存数据时用的key
	public static String CATEGORY_ALL="list";
	//首页 分页查询的条数
	public static int PRODUCT_COUNT=9;
	//商品分类显示每页显示数量
	public static  int PAGE_SIZE=12;
	//后台管理 订单显示数量
	public static  int ADMIN_PAGE_SIZE=5;
	//订单状态 0为未付款； 1 已付款 2.已发货 3 已完成
	public  static int  UNPAY=0;
	
	public  static int  PAIED=1;
	public  static int  SEND=2;
	public  static int  COMPLETED=3;
}
