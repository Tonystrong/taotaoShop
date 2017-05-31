package com.taotao.rest.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.taotao.common.utils.JsonUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemDescExample;
import com.taotao.pojo.TbItemDescExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.rest.component.RedisClient;
import com.taotao.rest.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper descMapper;
	@Autowired
	private TbItemParamItemMapper paramMapper;
	@Autowired
	private RedisClient redisClient;
	
	@Value("${REDIS_ITEM_KEY}")
	private String REDIS_ITEM_KEY;
	@Value("${ITEM_BASE_INFO_KEY}")
	private String ITEM_BASE_INFO_KEY;
	@Value("${ITEM_EXPIRE_SECOND}")
	private Integer ITEM_EXPIRE_SECOND;
	@Value("${ITEM_DESC_KEY}")
	private String ITEM_DESC_KEY;
	@Value("${ITEM_PARAM_KEY}")
	private String ITEM_PARAM_KEY;
	
	@Override
	public TbItem getItemById(Long itemId) {
		try {
			String json = redisClient.get(REDIS_ITEM_KEY+":"+ITEM_BASE_INFO_KEY+":"+itemId);
			if (StringUtils.isNotBlank(json)) {
				return JsonUtils.jsonToPojo(json, TbItem.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//第一次在数据库中取item数据
		TbItem item = itemMapper.selectByPrimaryKey(itemId);				
		
		//加入缓存
		try {
			redisClient.set(REDIS_ITEM_KEY+":"+ITEM_BASE_INFO_KEY+":"+itemId, JsonUtils.objectToJson(item));
			redisClient.expire(REDIS_ITEM_KEY+":"+ITEM_BASE_INFO_KEY+":"+itemId, ITEM_EXPIRE_SECOND);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return item;
	}

	@Override
	public TbItemDesc getItemDesc(Long itemId) {
		try {
			String json = redisClient.get(REDIS_ITEM_KEY+":"+ITEM_DESC_KEY+":"+itemId);
			if (StringUtils.isNotBlank(json)) {
				return JsonUtils.jsonToPojo(json, TbItemDesc.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//查询desc描述表中的item数据
		TbItemDesc itemDesc = descMapper.selectByPrimaryKey(itemId);
		
		//加入缓存
		try {
			redisClient.set(REDIS_ITEM_KEY+":"+ITEM_DESC_KEY+":"+itemId, JsonUtils.objectToJson(itemDesc));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return itemDesc;
	}

	@Override
	public TbItemParamItem getItemParamItem(Long itemId) {
		try {
			String json = redisClient.get(REDIS_ITEM_KEY+":"+ITEM_PARAM_KEY+":"+itemId);
			if (StringUtils.isNotBlank(json)) {
				return JsonUtils.jsonToPojo(json, TbItemParamItem.class);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//根据itemId获得itemparam
		TbItemParamItemExample example = new TbItemParamItemExample();
		com.taotao.pojo.TbItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> paramItems = paramMapper.selectByExampleWithBLOBs(example);
		
		//加入缓存
		if (paramItems!=null && paramItems.size()>0) {
			TbItemParamItem paramItem = paramItems.get(0);
			try {
				redisClient.set(REDIS_ITEM_KEY+":"+ITEM_PARAM_KEY+":"+itemId, JsonUtils.objectToJson(paramItem));
			} catch (Exception e) {
				e.printStackTrace();
			}
			return paramItem;
		}
		return null;
	}
}
