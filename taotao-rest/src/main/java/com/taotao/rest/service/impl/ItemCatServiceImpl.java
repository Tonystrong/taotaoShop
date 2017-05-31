package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.CatNode;
import com.taotao.pojo.ItemCatResult;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService{
	
	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public ItemCatResult getItemCatData() {
		
		List catList = getItemCatList(0l);
		ItemCatResult result = new ItemCatResult();
		result.setData(catList);
		return result;
	}

	private List getItemCatList(long parentId) {
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		List resultList = new ArrayList<>();
		int count = 0;
		for(TbItemCat itemCat : list) {
			if (count >= 14) {
				break;
			}
			//父节点显示
			if (itemCat.getIsParent()) {
				CatNode node = new CatNode();
				node.setUrl("/products/"+itemCat.getId()+".html");
				
				//第一节点
				if (itemCat.getParentId() == 0) {
					node.setName("<a href='/products/"+itemCat.getId()+
							".html'>"+itemCat.getName()+"</a>");
					count++;
				} else {
					node.setName(itemCat.getName());
				}
				node.setItems(getItemCatList(itemCat.getId()));
				resultList.add(node);
			} else {
				String item = "/products/"+itemCat.getId()+".html|"+itemCat.getName();
				resultList.add(item);
			}
		}
		return resultList;
	}
	
}
