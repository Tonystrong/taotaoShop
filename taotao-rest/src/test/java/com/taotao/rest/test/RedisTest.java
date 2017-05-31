package com.taotao.rest.test;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.rest.component.RedisClient;
import com.taotao.rest.component.impl.JedisClientSingle;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class RedisTest {
	
	
	@Test
	public void singleRedisTest() {
		Jedis jedis = new Jedis("192.168.93.129", 6379);
		jedis.set("test", "hello redis");
		String str = jedis.get("test");
		System.out.println(str);
		jedis.close();
	}
	
	
	@Test
	//单机版的连接池使用方法
	public void redisPool() {
		JedisPool jedisPool = new JedisPool("192.168.93.129", 6379);
		Jedis jedis = jedisPool.getResource();
		String str = jedis.get("test");
		System.out.println(str);
		jedis.close();
		jedisPool.close();
	}
	/*
	@Test
	//集群版本
	public void redisCluster() {
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.93.129", 7001));
		nodes.add(new HostAndPort("192.168.93.129", 7002));
		nodes.add(new HostAndPort("192.168.93.129", 7003));
		nodes.add(new HostAndPort("192.168.93.129", 7004));
		nodes.add(new HostAndPort("192.168.93.129", 7005));
		nodes.add(new HostAndPort("192.168.93.129", 7006));
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("name", "tony");
		jedisCluster.set("gender", "male");
		String gender = jedisCluster.get("gender");
		System.out.println(gender);
		jedisCluster.close();
	}
	*/
	@Test
	public void redisSpring() {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext(
										"classpath:spring/applicationContext-*.xml");
		RedisClient redisClient = applicationContext.getBean(RedisClient.class);
		redisClient.set("tony", "hoel");
		String key = redisClient.get("tony");
		System.out.println(key);
	}
}
