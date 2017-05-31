package com.taotao.portal.service;

import com.taotao.pojo.TbItem;

public interface ItemService {
	
	TbItem getItemById(Long itemId);
	
	String getDescById(Long itemId);
	
	String getParamItemById(Long itemId);
}
