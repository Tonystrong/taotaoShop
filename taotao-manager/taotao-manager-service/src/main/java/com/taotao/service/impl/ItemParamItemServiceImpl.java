package com.taotao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.service.ItemParamItemService;

@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {

	@Autowired
	private TbItemParamItemMapper itemMapper;
	
	@Override
	public TbItemParamItem getItemParamItemByItemId(Long itemId) {
		TbItemParamItemExample itemExample = new TbItemParamItemExample();
		Criteria criteria = itemExample.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> paramItemsList = itemMapper.selectByExampleWithBLOBs(itemExample);
		TbItemParamItem itemParamItem = paramItemsList.get(0);
		String paramData = itemParamItem.getParamData();
		
		return itemParamItem;
	}

}
