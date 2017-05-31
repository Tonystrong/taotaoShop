package com.taotao.sso.test;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.DigestUtils;

import com.taotao.sso.component.RedisClient;

import redis.clients.jedis.Jedis;

public class SsoTest {
	
	
	@Test
	public void Md5Test() {
		String pass = DigestUtils.md5DigestAsHex("123".getBytes());
		System.out.println(pass);
	}
	
	@Test
	public void redisTest() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
				"classpath:spring/applicationContext-*.xml");
		RedisClient redisClient = applicationContext.getBean(RedisClient.class);
		redisClient.set("REDIS_SESSION:"+"1222", "12324");
		String key = redisClient.get("REDIS_SESSION:"+"1222");
		System.out.println(key);
	}
	
	@Test
	public void singleRedisTest() {
		Jedis jedis = new Jedis("192.168.93.129", 6379);
		jedis.set("test", "hello redis");
		String str = jedis.get("test");
		System.out.println(str);
		jedis.close();
	}
}
