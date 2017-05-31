package com.taotao.service.impl;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;
	
	@Override
	public TbItem getTbItemById(Long id) {
		//TbItem item = itemMapper.selectByPrimaryKey(id);
		TbItemExample example = new TbItemExample();
		//创建查询条件
		Criteria criteria = example.createCriteria();
		criteria.andIdEqualTo(id);
		List<TbItem> list = itemMapper.selectByExample(example);
		//判断list中是否为空
		TbItem item = null;
		if (list != null && list.size() > 0) {
			item = list.get(0);
		}
		return item;
	}
	
	@Override
	public EasyUIDataGridResult getPageInfo(Integer page, Integer size) {
		PageHelper.startPage(page, size);
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		PageInfo<TbItem> listItem = new PageInfo<>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		result.setTotal(listItem.getTotal());
		return result;
	}

	@Override
	public TaotaoResult createItem(TbItem item, String desc, String itemParams) {
		//设置ItemID
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		//设置状态：1-正常，2-下架，3-删除'
		item.setStatus((byte) 1);
		//更新时间
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		//添加商品
		itemMapper.insert(item);
		
		//商品描述添加
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemDesc(desc);
		itemDesc.setUpdated(date);
		itemDesc.setCreated(date);
		itemDesc.setItemId(itemId);
		itemDescMapper.insert(itemDesc);
		
		//商品描述模板添加
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParams);
		System.out.println(itemParams);
		itemParamItemMapper.insert(itemParamItem);
		//放入TaotaoResult中
		return TaotaoResult.ok();
	}
}
