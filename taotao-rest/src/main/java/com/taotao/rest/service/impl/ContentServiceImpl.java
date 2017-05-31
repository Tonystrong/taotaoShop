package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.rest.component.RedisClient;
import com.taotao.rest.service.ContentService;

@Service
public class ContentServiceImpl implements ContentService {
	
	@Autowired
	private TbContentMapper contentMapper;
	@Autowired
	private RedisClient redisClient;
	
	@Value("${REDIS_CONTENT_KEY}")
	private String REDIS_CONTENT_KEY;
	@Value("${REDIS_TAOTAO_ITEM_KEY}")
	private String REDIS_TAOTAO_ITEM_KEY;
//	@Value("${REDIS_TAOTAO_ITME_EXPIRE}")
//	private String REDIS_TAOTAO_ITME_EXPIRE;
	
	@Override
	public List<TbContent> getContenList(Long cid) {
		try {
			//有缓存的话，读取缓存中内容
			String content = redisClient.get(cid+"");
			if (StringUtils.isNotBlank(content)) {
				return JsonUtils.jsonToList(content, TbContent.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		TbContentExample example = new TbContentExample();
		Criteria criteria = example.createCriteria();
		criteria.andCategoryIdEqualTo(cid);
		List<TbContent> list = contentMapper.selectByExampleWithBLOBs(example);
		
		//如果缓存中没有，查询出来后添加到缓存中
		try {
			redisClient.hset(REDIS_CONTENT_KEY, cid+"", JsonUtils.objectToJson(list));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
