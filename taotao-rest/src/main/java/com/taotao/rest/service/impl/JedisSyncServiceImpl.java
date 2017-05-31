package com.taotao.rest.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.rest.component.RedisClient;
import com.taotao.rest.service.JedisSyncService;

@Service
public class JedisSyncServiceImpl implements JedisSyncService {

	@Value("${REDIS_CONTENT_KEY}")
	private String REDIS_CONTENT_KEY;
	
	@Autowired
	private RedisClient redisClient;
	
	@Override
	public TaotaoResult delContentByCid(Long cid) {
		redisClient.hdel(REDIS_CONTENT_KEY, cid+"");
		return TaotaoResult.ok();
	}

}
