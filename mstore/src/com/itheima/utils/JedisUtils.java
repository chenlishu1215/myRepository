package com.itheima.utils;

import com.itheima.constant.Constant;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class JedisUtils {
	private static JedisPool jedisPool ;
	// 希望池子只加载一次
	static{
		//创建配置对象
		JedisPoolConfig config = new JedisPoolConfig();
		
		config.setMaxTotal(10);     // 最大连接数
		config.setMaxIdle(5);       // 最大空闲连接数
		
		//创建一个jedispool
		jedisPool = new JedisPool(config, "localhost",6379);
	}
	//获取一个jedis对象出来
	public static Jedis getJedis(){
		// 从池子中获取jedis对象
		//获取连接
		Jedis jedis = jedisPool.getResource();
		return jedis;
	}
	public static void delJedis(String str){
		getJedis().del(str);
	}
}
