package com.taotao.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemDescExample;
import com.taotao.pojo.TbItemDescExample.Criteria;
import com.taotao.service.ItemDescService;

@Service
public class ItemDescServiceImpl implements ItemDescService {

	@Autowired
	private TbItemDescMapper descMapper;
	
	@Override
	public TbItemDesc getItemDescByCid(Long itemId) {
		TbItemDescExample example = new TbItemDescExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemDesc> list = descMapper.selectByExampleWithBLOBs(example);
		return list.get(0);
	}
	
}
