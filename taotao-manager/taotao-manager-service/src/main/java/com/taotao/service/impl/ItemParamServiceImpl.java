package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.service.ItemParamService;

@Service
public class ItemParamServiceImpl implements ItemParamService {
	
	@Autowired
	private TbItemParamMapper itemParamMapper;
	
	@Override
	public EasyUIDataGridResult getItemParamForPage(Integer page, Integer size) {
		PageHelper.startPage(page, size);
		TbItemParamExample example = new TbItemParamExample();
		List<TbItemParam> paramList = itemParamMapper.selectByExampleWithBLOBs(example);
		PageInfo<TbItemParam> pageInfo = new PageInfo<>(paramList);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(paramList);
		return result;
	}
	
	@Override
	public TaotaoResult getItemParamByCatId(Long itemCatId) {
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		criteria.andItemCatIdEqualTo(itemCatId);
		List<TbItemParam> paramList = itemParamMapper.selectByExampleWithBLOBs(example);
		if (paramList!=null && paramList.size()>0) {
			TbItemParam itemParam = paramList.get(0);
			return TaotaoResult.ok(itemParam);
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult saveItemParamBlobs(Long cid, String paramData) {
		TbItemParam itemParam = new TbItemParam();
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		Date date = new Date();
		itemParam.setCreated(date);
		itemParam.setUpdated(date);
		itemParamMapper.insert(itemParam);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteItemParamByIds(String ids) {
		TbItemParamExample example = new TbItemParamExample();
		Criteria criteria = example.createCriteria();
		List<Long> values = new ArrayList<>();
		String[] idStrings = ids.split(",");
		for(int i=0; i<idStrings.length; i++) {
			values.add(Long.parseLong((idStrings[i])));
		}
		criteria.andIdIn(values);
		itemParamMapper.deleteByExample(example);
		return TaotaoResult.ok();
	}
}
