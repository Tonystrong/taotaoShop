package com.taotao.rest.service;

import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;

public interface ItemService {
	
	TbItem getItemById(Long itemId);
	
	TbItemDesc getItemDesc(Long itemId);
	
	TbItemParamItem getItemParamItem(Long itemId);
}
